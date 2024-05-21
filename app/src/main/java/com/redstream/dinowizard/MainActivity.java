package com.redstream.dinowizard;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentOnAttachListener;
import android.os.Build;
import android.os.Build.VERSION_CODES;

import com.google.android.material.slider.Slider;
import com.google.ar.core.Anchor;
import com.google.ar.core.Config;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Session;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.SceneView;
import com.google.ar.sceneform.Sceneform;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import com.gorisse.thomas.sceneform.light.LightEstimationConfig;
import com.redstream.dinowizard.helpers.Item;
import com.redstream.dinowizard.helpers.ModelCreator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements FragmentOnAttachListener {
    private static final String TAG = "MAIN_ACTIVITY";
    private static final double MIN_OPENGL_VERSION = 3.0;
    private ArFragment arFragment;
    private Slider scaleSlider;
    private ModelCreator modelCreator;
    private float raptorScale = 0.1f;
    private float dragonflyScale = 0.03f;
    private float rexScale = 0.02f;
    private int[] modelCount;
    private float scaleAdjusterValue;
    private ArrayList<Item> createdAnchorNodes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!checkIsSupportedDeviceOrFinish(this)) {
            Log.w(TAG, "App not supported!");
            return;
        }
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().addFragmentOnAttachListener(this);

        if (savedInstanceState == null) {
            if (Sceneform.isSupported(this)) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.arFragment, ArFragment.class, null)
                        .commit();
            }
        }

        modelCreator = new ModelCreator(this);
        modelCreator.initiateModels();

        modelCount = new int[]{0,0,0};
        createdAnchorNodes = new ArrayList<>();

        try {
            arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);
            scaleSlider = findViewById(R.id.scaleSlider);
        } catch (Exception e) {
            Log.e(TAG, "Error");
        }

    }

    @Override
    public void onAttachFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        if (fragment.getId() == R.id.arFragment) {
            arFragment = (ArFragment) fragment;
            arFragment.setOnTapArPlaneListener(this::onTapPlaneImpl);
            arFragment.setOnSessionConfigurationListener(this::onSessionConfiguration);
            arFragment.setOnViewCreatedListener(this::onViewCreated);
        }
    }

    public void onSessionConfiguration(Session session, Config config) {
        if (session.isDepthModeSupported(Config.DepthMode.AUTOMATIC)) {
            config.setDepthMode(Config.DepthMode.AUTOMATIC);
        }
    }

    public void onViewCreated(ArSceneView arSceneView) {
        arFragment.setOnViewCreatedListener(null);
        arSceneView._lightEstimationConfig = LightEstimationConfig.SPECTACULAR;
        // Fine adjust the maximum frame rate
        arSceneView.setFrameRateFactor(SceneView.FrameRate.FULL);

        scaleSlider.addOnChangeListener((slider, value, fromUser) -> {
            scaleAdjusterValue = value;

            for (Item item : createdAnchorNodes) {

                float scale;
                if (item.getDinoType() == Item.Type.RAPTOR) {
                    scale = raptorScale;
                } else if (item.getDinoType() == Item.Type.DRAGONFLY) {
                    scale = dragonflyScale;
                } else {
                    scale = rexScale;
                }

                scale += value;

                item.getAnchorNode().setLocalScale(new Vector3(scale, scale, scale));
            }
        });
    }

    public void onTapPlaneImpl(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
        if (modelCreator.getTrex() == null) {
            Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
            return;
        }

        float scale;
        ModelRenderable model;
        Item.Type dinoType;
        if (modelCount[0] > modelCount[1]) {
            dinoType = Item.Type.RAPTOR;
            model = modelCreator.getRaptor();
            scale = raptorScale;
            modelCount[1]++;
        } else if (modelCount[1] > modelCount[2]) {
            dinoType = Item.Type.DRAGONFLY;
            model = modelCreator.getDragonfly();
            scale = dragonflyScale;
            modelCount[2]++;
        } else {
            dinoType = Item.Type.REX;
            model = modelCreator.getTrex();
            scale = rexScale;
            modelCount[0]++;
        }

        //adjust scale
        scale += scaleAdjusterValue;

        // Create the Anchor.
        Anchor anchor = hitResult.createAnchor();
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setLocalScale(new Vector3(scale,scale,scale));
        anchorNode.setParent(arFragment.getArSceneView().getScene());
        createdAnchorNodes.add(new Item(anchorNode, dinoType));

        // Create the transformable model and add it to the anchor.
        TransformableNode modelNode = new TransformableNode(arFragment.getTransformationSystem());
        modelNode.setParent(anchorNode);
        modelNode.setRenderable(model)
                .animate(true).start();
        modelNode.select();
    }

    public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
        if (Build.VERSION.SDK_INT < VERSION_CODES.N) {
            Log.e(TAG, "Sceneform requires Android N or later");
            Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show();
            activity.finish();
            return false;
        }
        String openGlVersionString =
                ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
                        .getDeviceConfigurationInfo()
                        .getGlEsVersion();
        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later");
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                    .show();
            activity.finish();
            return false;
        }
        return true;
    }

}