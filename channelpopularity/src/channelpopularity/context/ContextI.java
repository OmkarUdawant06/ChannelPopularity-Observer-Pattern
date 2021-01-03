package channelpopularity.context;

import channelpopularity.state.StateName;

/**
 * Method is used to set the current state
 * as per Popularity score*/
public interface ContextI {
    void setCurrentState(StateName nextState);
}
