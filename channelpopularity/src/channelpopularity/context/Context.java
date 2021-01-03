package channelpopularity.context;

import channelpopularity.state.StateI;
import channelpopularity.state.StateName;
import channelpopularity.state.factory.SimpleStateFactoryI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Context implements ContextI{
    private Map<StateName, StateI> availableStates;
    private StateI curState;
    StateName currentState;
    StringBuilder text;

    /**
     * Initialize available states using Parameterised constructor */
    public Context(SimpleStateFactoryI stateFactoryIn, List<StateName> stateNames)
    {
        availableStates = new HashMap<StateName, StateI>();
        populateStates(stateFactoryIn, stateNames);
    }

    /**
     * Updates the current state*/
    @Override
    public void setCurrentState(StateName nextState) {
        if (availableStates.containsKey(nextState)) { // for safety.
            curState = availableStates.get(nextState);
        }
        this.currentState = nextState;
    }

    /**
     * Method to add video called in driver class*/
    public StringBuilder addVideo(String videoName){
        text = new StringBuilder();
        text.append(curState.ADD_VIDEO(videoName));
        return text;
    }

    /**
     * Method to remove video called in driver class*/
    public StringBuilder removeVideo(String videoName){
        text = new StringBuilder();
        text.append(curState.REMOVE_VIDEO(videoName));
        return text;
    }

    /**
     * Method to update metrics of video
     * called in driver class*/
    public StringBuilder metricsVideo(String videoName, String input){
        text = new StringBuilder();
        text.append(curState.METRICS(videoName, input));
        return text;
    }

    /**
     * Method to accept or reject ad for a video
     * called in driver class*/
    public StringBuilder adRequest(String videoName, String input){
        text = new StringBuilder();
        text.append(curState.AD_REQUEST(videoName, input));
        return text;
    }

    /**
     * Initializes available states*/
    public void populateStates(SimpleStateFactoryI stateFactoryIn, List<StateName> stateNames){
        for(StateName state : stateNames){
                availableStates.put(state, stateFactoryIn.create(state, this));
        }
        if (curState == null) {
            curState = availableStates.get(StateName.UNPOPULAR);
            currentState = StateName.UNPOPULAR;
        }
    }

    /**
     * Returns the current State*/
    public StateName getState() {
        return currentState;
    }
}

