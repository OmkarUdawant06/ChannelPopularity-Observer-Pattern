package channelpopularity.state;

import channelpopularity.context.Context;
import channelpopularity.exception.ContextException;

import java.util.List;
import java.util.Map;
import java.math.RoundingMode;
import java.math.BigDecimal;

public abstract class AbstractState implements StateI {
    float PopularityScore = 0;
    int PopularityScoreVideo = 0;
    private Context context;

    public AbstractState(Context context){
        this.context = context;
    }

    /**
     * This method updated the metrics and
     * calculates Popularity score
     * @param videoname for updating metrics of video
     * @param scoreData views, likes, dislikes
     * @return updated Popularity Score
     */
    @Override
    public StringBuilder METRICS(String videoname, String scoreData) {
        int viewsTemp = 0, likesTemp = 0, dislikesTemp = 0;
        StringBuilder text = new StringBuilder();

        try {
            if(videoName.containsKey(videoname)) {
                String viewS, likeS, dislikeS;
                int views, likes, dislikes;

                scoreData = scoreData.replace(",", " ");

                viewS = scoreData.substring(scoreData.indexOf("=") + 1, scoreData.indexOf(" "));
                views = Integer.parseInt(viewS);
                if(views < 0)
                {
                    throw new ContextException("VIEWS CANNOT BE LESS THAN 0!");
                }

                likeS = scoreData.substring(scoreData.indexOf("LIKES=") + 6, scoreData.indexOf(" D"));
                likes = Integer.parseInt(likeS);

                scoreData = scoreData.concat("]");
                dislikeS = scoreData.substring(scoreData.indexOf("DISLIKES=") + 9, scoreData.indexOf("]"));
                dislikes = Integer.parseInt(dislikeS);

                for(Map.Entry<String, List<Integer>> entry : videoName.entrySet()) {
                    List<Integer> values = entry.getValue();
                    String vname1 = entry.getKey();

                    if (vname1.equals(videoname)) {
                        viewsTemp = values.get(2) + views;
                        likesTemp = values.get(3) + likes;
                        dislikesTemp = values.get(4) + dislikes;

                        videoName.get(vname1).set(2, viewsTemp);
                        videoName.get(vname1).set(3, likesTemp);
                        videoName.get(vname1).set(4, dislikesTemp);


                        PopularityScoreVideo = viewsTemp + 2 * (likesTemp - dislikesTemp);

                        if (likesTemp <0) {
                            throw new ContextException("DECREASE IN LIKES IS MORE THAN THE TOTAL NUMBER OF LIKES FOR THE VIDEO!");
                        } else if (dislikesTemp < 0){
                            throw new ContextException("DECREASE IN DISLIKES IS MORE THAN THE TOTAL NUMBER OF DISLIKES FOR THE VIDEO!");
                        }

                        if (PopularityScoreVideo < 0) {
                            PopularityScoreVideo = 0;
                        }
                        videoName.get(vname1).set(1, PopularityScoreVideo);
                    }
                }
            } else{
                throw new ContextException("NO SUCH VIDEO FOUND IN DATA STRUCTURE!");
            }
            getScore();
            text.append(context.getState()).append("__POPULARITY_SCORE_UPDATE::").append(PopularityScore);
            checkState(context);
        }catch (ContextException | NumberFormatException e){
            e.printStackTrace();
            System.out.println(e);
            System.exit(0);
        }

        return text;
    }

    /**
     * Fetches score from Data Structure and updates values
     */
    public void getScore()
    {
        PopularityScore = 0;

        for(Map.Entry<String, List<Integer>> entry : videoName.entrySet()) {
            List<Integer> values = entry.getValue();
            PopularityScore = PopularityScore + values.get(1);
        }

        if(videoName.size() != 0) {
            PopularityScore = PopularityScore / videoName.size();
            PopularityScore = round(PopularityScore,2);
        }
    }

    /**
     * Checks whether to update current state depending on
     * Popularity score
     * @param context object of Context class
     */
    public void checkState(Context context){
        if(PopularityScore >= 0 && PopularityScore <= 1000) {
            context.setCurrentState(StateName.UNPOPULAR);
        } else if(PopularityScore > 1000 && PopularityScore <= 10000){
            context.setCurrentState(StateName.MILDLY_POPULAR);
        } else if(PopularityScore > 10000 && PopularityScore <= 100000) {
            context.setCurrentState(StateName.HIGHLY_POPULAR);
        } else if(PopularityScore > 100000 && PopularityScore <= INT_MAX) {
            context.setCurrentState(StateName.ULTRA_POPULAR);
        }
    }


    /**
     * Checks if Ad is Accepted or Rejected
     * @param length Ad length
     * @param minValue minimum condition for Ad to be accepted
     * @param maxValue maximum condition for Ad to be accepted
     * @param stateName current State name
     * @return whether Ad accepted or Rejected
     */
    public StringBuilder adRequestCheck(int length, int minValue, int maxValue, StateName stateName)
    {
        StringBuilder text = new StringBuilder();
        if(checkLengthIfTrue(length, minValue, maxValue) == 1){
            text.append(stateName).append("__AD_REQUEST::APPROVED");
        } else{
            text.append(stateName).append("__AD_REQUEST::REJECTED");
        }
        return text;
    }

    /**
     * Checks for Ad to be in range
     * @param length length of Ad
     * @param minValue minimum condition for Ad to be accepted
     * @param maxValue maximum condition for Ad to be accepted
     * @return 1 if in range or 0 if not in range
     */
    public int checkLengthIfTrue(int length, int minValue, int maxValue)
    {
        if(length > minValue && length <= maxValue)
        {
            return 1;
        }
        else {
            return 0;
        }
    }

    /**
     * Function to Round upto specified places */
    public static float round(float value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }
}
