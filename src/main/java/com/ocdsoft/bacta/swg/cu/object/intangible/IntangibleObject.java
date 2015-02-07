package com.ocdsoft.bacta.swg.cu.object.intangible;

import com.ocdsoft.bacta.swg.cu.object.SceneObject;

public abstract class IntangibleObject extends SceneObject {
    @Override
    public int getOpcode() {
        return 0x49544E4F;
    } //'ITNO'

    // IntangibleObject03
//    private final AutoDeltaInt count = new AutoDeltaInt(0, sharedPackage);
}
