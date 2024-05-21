package com.redstream.dinowizard.helpers;

import android.content.Context;
import android.util.Log;

import com.google.ar.sceneform.rendering.ModelRenderable;
import com.redstream.dinowizard.R;

public class ModelCreator {
    static String TAG = "DINO_CREATOR";
    private final Context context;
    private ModelRenderable rex = null;
    private ModelRenderable raptor = null;
    private ModelRenderable dragonfly = null;

    public ModelCreator(Context context) {
        this.context = context;
    }

    private void buildRex() {
        ModelRenderable.builder().setSource(context, R.raw.rex)
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build()
                .thenAccept(render -> rex = render)
                .exceptionally(throwable -> {
                    Log.e(TAG, "Unable to load model.", throwable);
                    return null;
                });
    }

    private void buildRaptor() {
        ModelRenderable.builder().setSource(context, R.raw.raptor)
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build()
                .thenAccept(render -> raptor = render)
                .exceptionally(throwable -> {
                    Log.e(TAG, "Unable to load model.", throwable);
                    return null;
                });
    }

    private void buildDragonfly() {
        ModelRenderable.builder().setSource(context, R.raw.dragonfly)
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build()
                .thenAccept(render -> dragonfly = render)
                .exceptionally(throwable -> {
                    Log.e(TAG, "Unable to load model.", throwable);
                    return null;
                });
    }

//Toast.makeText(MainActivity.getContext(), "Adding...", Toast.LENGTH_SHORT).show();
    public ModelRenderable getTrex() {
        if (rex == null) {
            buildRex();
        }
        return rex;
    }

    public ModelRenderable getRaptor() {
        if (raptor == null) {
            buildRaptor();
        }
        return raptor;
    }

    public ModelRenderable getDragonfly() {
        if (dragonfly == null) {
            buildDragonfly();
        }
        return dragonfly;
    }

    public void initiateModels() {
        if (rex == null) {
            buildRex();
        }
        if (raptor == null) {
            buildRaptor();
        }
        if (dragonfly == null) {
            buildDragonfly();
        }
    }
}
