package com.redstream.dinowizard.models;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.google.ar.sceneform.rendering.ModelRenderable;
import com.redstream.dinowizard.MainActivity;
import com.redstream.dinowizard.R;

public class ModelCreator {
    static String TAG = "DINO_CREATOR";
    private final Context context;
    private ModelRenderable rex = null;
    private ModelRenderable raptor = null;
    private ModelRenderable dragonfly = null;
    //TODO:make models
    private String link = "https://firebasestorage.googleapis.com/v0/b/gemas-list-1662485803384.appspot.com/o/models%2Fdragonfly.glb?alt=media&token=3419f839-e222-4b17-b8fc-ecffe9c24579";
    private final String rexResource = link;
    private final String raptorResource = link;
    private final String dragonflyResource = link;

    public ModelCreator(Context context) {
        this.context = context;
    }

//    private void buildRex() {
//        ModelRenderable.builder().setSource(context, Uri.parse(rexResource), true)
//                .build()
//                .thenAccept(render -> {
//                    rex = render;
//                })
//                .exceptionally(throwable -> {
//                    Log.e(TAG, "Unable to load renderable.", throwable);
//                    return null;
//                });
//    }

//    private void buildRaptor() {
//        ModelRenderable.builder().setSource(context, Uri.parse(raptorResource), true)
//                .build()
//                .thenAccept(render -> {
//                    raptor = render;
//                })
//                .exceptionally(throwable -> {
//                    Log.e(TAG, "Unable to load renderable.", throwable);
//                    return null;
//                });
//    }

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

//        Toast.makeText(MainActivity.getContext(), "Adding...", Toast.LENGTH_SHORT).show();
//    public ModelRenderable getTrex() {
//        if (rex == null) {
//            buildRex();
//        }
//        return rex;
//    }

//    public ModelRenderable getRaptor() {
//        if (raptor == null) {
//            buildRaptor();
//        }
//        return raptor;
//    }

    public ModelRenderable getDragonfly() {
        if (dragonfly == null) {
            buildDragonfly();
        }
        return dragonfly;
    }

    public void initiateModels() {
//        if (rex == null) {
//            buildRex();
//        }
//        if (raptor == null) {
//            buildRaptor();
//        }
        if (dragonfly == null) {
            buildDragonfly();
        }
    }
}
