package channelpopularity.state.factory;

import channelpopularity.context.Context;
import channelpopularity.state.StateI;
import channelpopularity.state.StateName;

/**
 * Create method for creating states
 */
public interface SimpleStateFactoryI {
    StateI create(StateName stateName, Context context);
}
