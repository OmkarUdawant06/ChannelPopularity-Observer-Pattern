package channelpopularity.state.factory;

import channelpopularity.context.Context;
import channelpopularity.state.*;

import java.util.Iterator;

public class SimpleStateFactory implements SimpleStateFactoryI {
    private static SimpleStateFactory instance = new SimpleStateFactory();
    StateI state = null;

    public SimpleStateFactory()
    {
    }

    /**
     * Creates States
     * @param stateName of enum StateName
     * @param context of Context class
     * @return state object
     */
    @Override
    public StateI create(StateName stateName, Context context) {
        if (stateName == StateName.UNPOPULAR){
            state = new Unpopular(context);
        } else if (stateName == StateName.MILDLY_POPULAR){
            state = new Mildly_Popular(context);
        } else if (stateName == StateName.HIGHLY_POPULAR){
            state = new Highly_Popular(context);
        } else {
            state = new Ultra_Popular(context);
        }
        return state;
    }
}
