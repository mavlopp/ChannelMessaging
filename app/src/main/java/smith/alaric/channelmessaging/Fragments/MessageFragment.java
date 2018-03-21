package smith.alaric.channelmessaging.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.internal.Streams;

import org.apache.http.NameValuePair;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import smith.alaric.channelmessaging.HttpPostHandler;
import smith.alaric.channelmessaging.LoginActivity;
import smith.alaric.channelmessaging.MessageArrayAdapter;
import smith.alaric.channelmessaging.OnDownloadListener;
import smith.alaric.channelmessaging.PostRequest;
import smith.alaric.channelmessaging.R;
import smith.alaric.channelmessaging.db.UserDatasource;
import smith.alaric.channelmessaging.model.Message;
import smith.alaric.channelmessaging.model.MessageList;
import smith.alaric.channelmessaging.model.UploadFileToServer;

/**
 * Created by smithal on 26/02/2018.
 */
public class MessageFragment extends Fragment implements OnDownloadListener, View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView lv;
    private EditText tv;
    private Button send;
    private Button sendImg;
    private long chanId;
    private List<NameValuePair> mParameters = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_message, container);
        Context c = getActivity().getApplicationContext();

        lv = (ListView) v.findViewById(R.id.listView2);
        tv = (EditText) v.findViewById(R.id.editText);
        send = (Button) v.findViewById(R.id.send);
        sendImg = (Button) v.findViewById(R.id.btImg);

        send.setOnClickListener(this);
        sendImg.setOnClickListener(this);

        final Handler handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                Post(chanId);
                handler.postDelayed(this, 1000);
            }
        };

        handler.postDelayed(r, 1000);
        lv.setOnItemClickListener(this);
        return v;
    }

    @Override
    public void onDownloadComplete(String downloadedContent) {
        if(getActivity() != null) {
            Gson gson = new Gson();
            MessageList obj = gson.fromJson(downloadedContent, MessageList.class);

            lv.setAdapter(new MessageArrayAdapter(getActivity().getApplicationContext(), obj.getList()));
        }

    }

    @Override
    public void onDownloadError(String error) {
        Context c = getActivity().getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast t = Toast.makeText(c, error, duration);
        t.show();
    }


    @Override
    public void onClick(View v) {
        double PICTURE_REQUEST_CODE = 200;
        switch (v.getId()){
            case R.id.send:
                String token;
                SharedPreferences settings = getActivity().getSharedPreferences(LoginActivity.PREFS_NAME, 0);
                token = settings.getString("accesstoken", null);

                HashMap<String, String> myMap = new HashMap<String, String>();
                myMap.put("accesstoken", token);
                myMap.put("channelid", String.valueOf(chanId));
                myMap.put("message", tv.getText().toString());

                PostRequest p = new PostRequest("http://www.raphaelbischof.fr/messaging/?function=sendmessage", myMap);
                HttpPostHandler handler = new HttpPostHandler();
                handler.addOnDownloadListener(this);
                handler.execute(p);
                tv.setText("");
                break;
            case R.id.btImg:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //Création de l’appel à l’application appareil photo pour récupérer une image
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        Context c = getActivity().getApplicationContext();
                        int duration = Toast.LENGTH_LONG;
                        Toast t = Toast.makeText(c, ex.toString(), duration);
                        t.show();
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                            Uri photoURI = FileProvider.getUriForFile(getActivity(),
                                "smith.alaric.channelmessaging.fileprovider",
                                photoFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI); //Emplacement de l’image stockée
                        //takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(intent, (int)PICTURE_REQUEST_CODE);
                    }
                }
               // startActivityForResult(intent, (int)PICTURE_REQUEST_CODE);
        }

    }

    String mCurrentPhotoPath;
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        Context c = getActivity().getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast t = Toast.makeText(c, storageDir.toString(), duration);
        t.show();
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void Post() {
        Long chan_id;
        chan_id = getActivity().getIntent().getLongExtra("CHAN_ID",999);
        //chan_id = bundle.getString("CHAN_ID");
        Post(chan_id);
    }

    public void Post(double chan_id){
        String token = "";
        if(getActivity() != null) {
            SharedPreferences settings = getActivity().getSharedPreferences(LoginActivity.PREFS_NAME, 0);
            token = settings.getString("accesstoken", null);
        }



        HashMap<String, String> myMap = new HashMap<String, String>();
        myMap.put("accesstoken", token);
        myMap.put("channelid", ""+chan_id);
        PostRequest p = new PostRequest("http://www.raphaelbischof.fr/messaging/?function=getmessages", myMap);
        HttpPostHandler handler = new HttpPostHandler();
        handler.addOnDownloadListener(this);
        handler.execute(p);
    }

    public void changeChannel(final long chan_id){
        this.chanId = chan_id;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
        final Message item = (Message) parent.getItemAtPosition(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Attention")
                .setMessage("Voulez-vous ajouter cette personne à votre liste d'amis ?")
                .setCancelable(false)
                .setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        UserDatasource ud = new UserDatasource(getContext());
                        ud.open();
                        ud.createFriend(item.getUsername(), item.getImageUrl());
                        ud.close();
                    }
                }).setNegativeButton("NON", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }
        );
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 200){
            if(resultCode == Activity.RESULT_OK){
                try{
                    resizeFile(new File(mCurrentPhotoPath), getActivity());
                    long chan_id = getActivity().getIntent().getLongExtra("CHAN_ID",999);
                    final String id = String.valueOf(chan_id);
                    //final String token = "";
                    String token = "";
                    if(getActivity() != null) {
                        SharedPreferences settings = getActivity().getSharedPreferences(LoginActivity.PREFS_NAME, 0);
                        token = settings.getString("accesstoken", null);
                    }
                    final String to = token;
                    mParameters.add(new NameValuePair() {
                        @Override
                        public String getName() {
                            return "accesstoken";
                        }

                        @Override
                        public String getValue() {
                            return to;
                        }
                    });
                    mParameters.add(new NameValuePair() {
                        @Override
                        public String getName() {
                            return "channelid";
                        }

                        @Override
                        public String getValue() {
                            return id;
                        }
                    });
                    UploadFileToServer upload = new UploadFileToServer(getActivity(), mCurrentPhotoPath, mParameters, new UploadFileToServer.OnUploadFileListener() {
                        @Override
                        public void onResponse(String result) {
                            Context c = getActivity().getApplicationContext();
                            int duration = Toast.LENGTH_LONG;
                            Toast t = Toast.makeText(c, "GG", duration);
                            t.show();
                        }

                        @Override
                        public void onFailed(IOException error) {
                            Context c = getActivity().getApplicationContext();
                            int duration = Toast.LENGTH_LONG;
                            Toast t = Toast.makeText(c, "PAS BON", duration);
                            t.show();
                        }
                    });
                } catch (IOException ex) {
                    Context c = getActivity().getApplicationContext();
                    int duration = Toast.LENGTH_LONG;
                    Toast t = Toast.makeText(c, ex.toString(), duration);
                    t.show();
                }
            } else {
                Context c = getActivity().getApplicationContext();
                int duration = Toast.LENGTH_LONG;
                Toast t = Toast.makeText(c, "PAS OK" , duration);
                t.show();

            }
        } else {
            Context c = getActivity().getApplicationContext();
            int duration = Toast.LENGTH_LONG;
            Toast t = Toast.makeText(c, "PAS 200", duration);
            t.show();
        }
    }
    //decodes image and scales it to reduce memory consumption
    private void resizeFile(File f,Context context) throws IOException {
        //Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(new FileInputStream(f),null,o);

        //The new size we want to scale to
        final int REQUIRED_SIZE=400;

        //Find the correct scale value. It should be the power of 2.
        int scale=1;
        while(o.outWidth/scale/2>=REQUIRED_SIZE && o.outHeight/scale/2>=REQUIRED_SIZE)
            scale*=2;

        //Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize=scale;
        Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        int i = getCameraPhotoOrientation(context, Uri.fromFile(f),f.getAbsolutePath());
        if (o.outWidth>o.outHeight)
        {
            Matrix matrix = new Matrix();
            matrix.postRotate(i); // anti-clockwise by 90 degrees
            bitmap = Bitmap.createBitmap(bitmap , 0, 0, bitmap .getWidth(), bitmap .getHeight(), matrix, true);
        }
        try {
            f.delete();
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static int getCameraPhotoOrientation(Context context, Uri imageUri, String imagePath) throws IOException {
        int rotate = 0;
        context.getContentResolver().notifyChange(imageUri, null);
        File imageFile = new File(imagePath);
        ExifInterface exif = new ExifInterface(
                imageFile.getAbsolutePath());
        int orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_270:
                rotate = 270;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                rotate = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotate = 90;
                break;
        }
        return rotate;
    }
}
