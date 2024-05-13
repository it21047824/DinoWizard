package com.redstream.dinowizard.models;

import android.content.Context;
import android.util.Log;

import com.google.ar.sceneform.rendering.ModelRenderable;
import com.redstream.dinowizard.R;

public class ModelCreator {
    static String TAG = "DINO_CREATOR";
    private Context context;
    private ModelRenderable trex = null;
    private ModelRenderable raptor = null;
    private ModelRenderable dragonfly = null;
    //TODO:make models
    private final int trexResource = R.raw.raptor;
    private final int raptorResource = R.raw.raptor;
    private final int draagonflyResource = R.raw.dragonfly;

    public ModelCreator(Context context) {
        this.context = context;
    }

    public ModelRenderable getTrex() {
        if (trex == null) {
            ModelRenderable.builder().setSource(context, trexResource)
                    .build()
                    .thenAccept(renderable -> {
                        trex = renderable;
                    })
                    .exceptionally(throwable -> {
                        Log.e(TAG, "Unable to load renderable.", throwable);
                        return null;
                    });
        }
        return trex;
    }

    public ModelRenderable getRaptor() {
        if (raptor == null) {
            ModelRenderable.builder().setSource(context, raptorResource)
                    .setAsyncLoadEnabled(true)
                    .build()
                    .thenAccept(renderable -> raptor = renderable)
                    .exceptionally(throwable -> {
                        Log.e(TAG, "Unable to load renderable.", throwable);
                        return null;
                    });
        }
        return raptor;
    }

    public ModelRenderable getDragonfly() {
        if (dragonfly == null) {
            ModelRenderable.builder().setSource(context, draagonflyResource)
                    .build()
                    .thenAccept(renderable -> dragonfly = renderable)
                    .exceptionally(throwable -> {
                        Log.e(TAG, "Unable to load renderable.", throwable);
                        return null;
                    });
        }
        return dragonfly;
    }

    public void initiateModels() {
        if (trex == null) {
            ModelRenderable.builder().setSource(context, trexResource)
                    .build()
                    .thenAccept(renderable -> {
                        trex = renderable;
                    })
                    .exceptionally(throwable -> {
                        Log.e(TAG, "Unable to load renderable.", throwable);
                        return null;
                    });
        }
        if (raptor == null) {
            ModelRenderable.builder().setSource(context, raptorResource)
                    .build()
                    .thenAccept(renderable -> raptor = renderable)
                    .exceptionally(throwable -> {
                        Log.e(TAG, "Unable to load renderable.", throwable);
                        return null;
                    });
        }
        if (dragonfly == null) {
            ModelRenderable.builder().setSource(context, draagonflyResource)
                    .build()
                    .thenAccept(renderable -> dragonfly = renderable)
                    .exceptionally(throwable -> {
                        Log.e(TAG, "Unable to load renderable.", throwable);
                        return null;
                    });
        }
    }
}
