package channelpopularity.state;

import channelpopularity.context.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Highly_Popular extends AbstractState{
    private Context context;
    public Highly_Popular(Context context){
        super(context);
        this.context = context;
    }

    /**
     * Updates Current State
     * @param nextState Set this state as current state
     */
    @Override
    public void setCurrentStateI(StateName nextState) {
        checkState(this.context);
    }

    /**
     * Adds video to Data Structure(HashMap)
     * @param vname video name
     * @return Output of Added Video
     */
    @Override
    public StringBuilder ADD_VIDEO(String vname) {
        StringBuilder text = new StringBuilder();
        if(!videoName.containsKey(vname)) {
            videoName.put(vname, new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0)));

            for (Map.Entry<String, List<Integer>> entry : videoName.entrySet()) {
                List<Integer> values = entry.getValue();
                String vname1 = entry.getKey();
                if (values.get(0).equals(0) && vname1.equals(vname)) {
                    videoName.get(vname1).set(0, 1);
                    text.append(context.getState()).append("__VIDEO_ADDED::").append(vname1);
                }
            }
        } else {
            text.append("ERROR ADDING VIDEO! ").append(vname).append(" MAY ALREADY EXISTS OR FORMAT INVALID!");
            System.exit(0);
        }
        getScore();
        checkState(context);
        return text;
    }

    /**
     * Removes the video from Data Structure(HashMap)
     * @param vname video name
     * @return Output of Removed Video
     */
    @Override
    public StringBuilder REMOVE_VIDEO(String vname) {
        StringBuilder text = new StringBuilder();
        if(videoName.containsKey(vname)){
            for (Map.Entry<String, List<Integer>> entry : videoName.entrySet()) {
                List<Integer> values = entry.getValue();
                String vname1 = entry.getKey();
                if (values.get(0).equals(1) && vname1.equals(vname)) {
                    videoName.get(vname1).set(0, 0);
                    videoName.remove(entry);
                    text.append(context.getState()).append("__VIDEO_REMOVED::").append(vname1);
                }
            }
            videoName.remove(vname);
        } else{
            text.append("ERROR REMOVING VIDEO! ").append(vname).append(" DOES NOT EXIST OR FORMAT INVALID!");
            System.exit(0);
        }
        getScore();
        checkState(context);
        return text;
    }

    /**
     * Decides whether Ad is accepted or rejected
     * depending on current State
     * @param videoname video name
     * @param lengthS length of video
     * @return Ad Accepted or Rejected
     */
    @Override
    public StringBuilder AD_REQUEST(String videoname, String lengthS) {
        StringBuilder text = new StringBuilder();
        int length = Integer.parseInt(lengthS);
        videoname = videoname.substring(0,videoname.length()-1);

        if(videoName.containsKey(videoname) && length > 0) {
            switch (context.getState()) {
                case UNPOPULAR:
                    text.append(adRequestCheck(length, 1, 10, context.getState()));
                    break;
                case MILDLY_POPULAR:
                    text.append(adRequestCheck(length, 1, 20, context.getState()));
                    break;
                case HIGHLY_POPULAR:
                    text.append(adRequestCheck(length, 1, 30, context.getState()));
                    break;
                case ULTRA_POPULAR:
                    text.append(adRequestCheck(length, 1, 40, context.getState()));
                    break;
            }
        } else{
            text.append("ERROR PROCESSING AD_REQUEST! ").append(videoname).append(" DOES NOT EXIST OR LENGTH IS NEGATIVE!");
            System.exit(0);
        }
        return text;
    }
}
