package com.cropcart.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cropcart.R;
import com.cropcart.preferences.SharedPref;
import com.cropcart.ui.MainActivity;
import com.google.android.flexbox.FlexboxLayout;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by BHUSRI on 10/6/2017.
 */

public class LandDetails extends Fragment implements View.OnClickListener, UploadStatusDelegate {
    private static final String MYTAG = "LANDDETAILS";
    private FlexboxLayout flexboxLayout;
    private String keys[] = {"Cabbage", "Carrot", "Broccoli", "Brinjal", "Onion", "Tomato"};
    private ArrayList<String> selectedcategories = new ArrayList<>();
    private List<TextView> textviews = new ArrayList<>();
    private TextView size1, size2, size3, size4, size5;
    private int sizechosen = 0;
    private TextView submit;
    private ImageView choosephoto;
    private Uri selectedImage;
    private String coverpic;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.landdetails, container, false);
        flexboxLayout = v.findViewById(R.id.flexbox);
        choosephoto = v.findViewById(R.id.choosepic);
        submit = v.findViewById(R.id.submit);
        size1 = v.findViewById(R.id.size);
        size2 = v.findViewById(R.id.size2);
        size3 = v.findViewById(R.id.size3);
        size4 = v.findViewById(R.id.size4);
        size5 = v.findViewById(R.id.size5);
        size1.setOnClickListener(this);
        size2.setOnClickListener(this);
        size3.setOnClickListener(this);
        size4.setOnClickListener(this);
        size5.setOnClickListener(this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedcategories.size() > 0) {
                    if (selectedImage != null) {
                        coverpic = String.valueOf(Calendar.getInstance().getTimeInMillis());
                        uploadMultipart(coverpic, getRealPathFromURI(selectedImage));
                    } else {
                        coverpic = "none";
                        savelanddetails();
                    }
                } else {

                    Toast.makeText(getActivity(), "Please Choose atleast one crop", Toast.LENGTH_SHORT).show();
                }
            }
        });

        choosephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v2 = LayoutInflater.from(getActivity()).inflate(R.layout.choosepic, null);
                TextView capture, gallery, cancel;
                capture = v2.findViewById(R.id.capture);
                gallery = v2.findViewById(R.id.gallery);
                cancel = v2.findViewById(R.id.cancel);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(v2);
                final AlertDialog dialog = builder.create();
                dialog.show();
                capture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 1);
                        dialog.dismiss();
                    }
                });
                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent pictureActionIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pictureActionIntent, 1);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        for (int i = 0; i < keys.length; i++) {
            View subchild = getLayoutInflater().inflate(R.layout.single_chip, null);
            final TextView tv = subchild.findViewById(R.id.txt_title);
            tv.setText(keys[i]);
            final int finalI = i;
            final String[] finalKeys = keys;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tv.getTag() != null && tv.getTag().equals("selected")) {
                        tv.setTag("unselected");
                        tv.setBackgroundResource(R.drawable.chip_unselected);
                        tv.setTextColor(ContextCompat.getColor(getActivity(), R.color.filters_chips));
                        removeFromSelectedMap(finalKeys[finalI]);
                    } else {
                        tv.setTag("selected");
                        tv.setBackgroundResource(R.drawable.chip_selected);
                        tv.setTextColor(ContextCompat.getColor(getActivity(), R.color.filters_header));
                        addToSelectedMap(finalKeys[finalI]);
                    }
                }
            });

            if (selectedcategories.contains(keys[finalI])) {
                tv.setTag("selected");
                tv.setBackgroundResource(R.drawable.chip_selected);
                tv.setTextColor(ContextCompat.getColor(getActivity(), R.color.filters_header));
            } else {
                tv.setBackgroundResource(R.drawable.chip_unselected);
                tv.setTextColor(ContextCompat.getColor(getActivity(), R.color.filters_chips));
            }
            textviews.add(tv);

            flexboxLayout.addView(subchild);
        }
        return v;
    }

    private void savelanddetails() {
        StringRequest request = new StringRequest(Request.Method.POST, Urls.domain + Urls.savelanddetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                JSONObject obj = new JSONObject();
                for (int i = 0; i < selectedcategories.size(); i++) {
                    try {
                        obj.put("" + i, selectedcategories.get(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                SharedPref pref = new SharedPref(getActivity());
                map.put("size", String.valueOf(sizechosen));
                map.put("crops", obj.toString());
                map.put("userid", pref.getUserid());
                map.put("coverpic", coverpic);
                Log.d(MYTAG, "getParams: " + map);
                return map;
            }
        };
        Volley.newRequestQueue(getActivity()).add(request);
    }

    private void addToSelectedMap(String value) {
        selectedcategories.add(value);
    }

    private void removeFromSelectedMap(String value) {
        selectedcategories.remove(value);
    }

    @Override
    public void onClick(View view) {
        size1.setBackgroundResource(R.drawable.roundedbutton3);
        size2.setBackgroundResource(R.drawable.roundedbutton3);
        size3.setBackgroundResource(R.drawable.roundedbutton3);
        size4.setBackgroundResource(R.drawable.roundedbutton3);
        size5.setBackgroundResource(R.drawable.roundedbutton3);
        switch (view.getId()) {
            case R.id.size:
                sizechosen = 0;
                size1.setBackgroundResource(R.drawable.roundedbutton4);
                break;
            case R.id.size2:
                sizechosen = 1;
                size2.setBackgroundResource(R.drawable.roundedbutton4);
                break;
            case R.id.size3:
                sizechosen = 2;
                size3.setBackgroundResource(R.drawable.roundedbutton4);
                break;
            case R.id.size4:
                sizechosen = 3;
                size4.setBackgroundResource(R.drawable.roundedbutton4);
                break;
            case R.id.size5:
                sizechosen = 4;
                size5.setBackgroundResource(R.drawable.roundedbutton4);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == getActivity().RESULT_OK && data != null) {
            selectedImage = data.getData();
            ImageLoader.getInstance().displayImage(String.valueOf(selectedImage), choosephoto);
            // choosephoto.setImageURI(selectedImage);
        }
    }

    public void uploadMultipart(String name, String path) {
        String mydomain = Urls.domain + "upload.php";
        //getting name for the image
        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();
            System.setProperty("http.keepAlive", "false");
            //Creating a multi part request
            String mytype = path.substring(path.lastIndexOf("."));
            Log.d("MYTYPEE", "uploadMultipart: " + path);
            if (mydomain.length() > 0) {
                MultipartUploadRequest request = new MultipartUploadRequest(getActivity(), mydomain)
                        .addFileToUpload(path, "image") //Adding file
                        .addParameter("type", mytype)
                        .setMethod("POST")
                        .addParameter("name", name) //Adding text parameter to the request
                        .setNotificationConfig(new UploadNotificationConfig().setRingToneEnabled(false))
                        .setMaxRetries(2);
                request.setDelegate(LandDetails.this);
                request.startUpload();
            }
            //Starting the upload
        } catch (Exception exc) {
            exc.printStackTrace();
            Toast.makeText(getActivity(), exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onProgress(Context context, UploadInfo uploadInfo) {

    }

    @Override
    public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {

    }

    @Override
    public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
        savelanddetails();
    }

    @Override
    public void onCancelled(Context context, UploadInfo uploadInfo) {

    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getActivity().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

}
