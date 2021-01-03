package channelpopularity.state;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Declare actions to perform on video
 */
public interface StateI {
    int INT_MAX = 2147483647;
    Map<String, List<Integer>> videoName = new HashMap<String, List<Integer>>();
    StringBuilder ADD_VIDEO(String videoName) throws NullPointerException, IllegalArgumentException;
    StringBuilder REMOVE_VIDEO(String vname)throws NullPointerException, IllegalArgumentException;
    StringBuilder AD_REQUEST(String videoname, String length)throws NullPointerException, IllegalArgumentException;
    StringBuilder METRICS(String videoname, String scoreData)throws NullPointerException, IllegalArgumentException;
    void setCurrentStateI(StateName stateName);
}
