package hellogbye.com.hellogbyeandroid.fragments.settings;

import android.app.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.MainActivity;
import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.adapters.settingaccount.AccountSettingsAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.vo.statics.BookingRequestVO;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.UserProfileVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtilityPermissions;
import hellogbye.com.hellogbyeandroid.views.DividerItemDecoration;
import hellogbye.com.hellogbyeandroid.views.FontTextView;
import hellogbye.com.hellogbyeandroid.views.RoundedImageView;

public class AccountSettingsFragment extends HGBAbstractFragment {


    private RoundedImageView account_details_image;
    private FontTextView account_settings_details_name;
    private FontTextView account_settings_details_city;
    private Activity activity;
    private UserProfileVO currentUser;
    private String userChoosenTask;
    private Bitmap thumbnail;

    public AccountSettingsFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getFlowInterface().selectBottomBar(R.id.bb_menu_my_account);
        getFlowInterface().bottomBarVisible(true);
    }

    public static Fragment newInstance(int position) {
        Fragment fragment = new AccountSettingsFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }



    private void getUserData(){
        ConnectionManager.getInstance(getActivity()).getUserProfile(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

                UserProfileVO mCurrentUser = (UserProfileVO) data;
                getActivityInterface().setCurrentUser(mCurrentUser);
                initializeUserData();
               // ((MainActivityBottomTabs)getActivity()).updateProfilePicture(thumbnail);

            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }

    private void initializeUserData(){
        currentUser = getActivityInterface().getCurrentUser();

        HGBUtility.getAndSaveUserImage(currentUser.getAvatar(), account_details_image, null);
       // HGBUtility.loadRoundedImage(getActivity().getApplicationContext(),currentUser.getAvatar(),account_details_image);

        String title = currentUser.getTitle();
        //TODO remove when server change the title without spaces
        if(title != null) {
            title = title.trim();
        } else{
            title = "";
        }
        String userName = title +" "+ currentUser.getFirstname() + " " + currentUser.getLastname();
        account_settings_details_name.setText(userName);

        String address = currentUser.getAddress();
        if(address == null){
            address = "";
        }
        String userCity = currentUser.getCity() + " " + address;
        account_settings_details_city.setText(userCity);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.account_settings_main_layout, container, false);

        activity = getActivity();

        String[] account_settings = getResources().getStringArray(R.array.settings_account);

        RecyclerView account_settings_preferences_list = (RecyclerView)rootView.findViewById(R.id.account_settings_preferences_list);

        LayoutInflater li = LayoutInflater.from(getActivity());
        final View promptsView = li.inflate(R.layout.popup_layout_log_out, null);
        final EditText input = (EditText) promptsView
                .findViewById(R.id.companion_editTextDialog);

        final FontTextView text = (FontTextView) promptsView
                .findViewById(R.id.component_popup_logout_text);


        LinearLayout btn_account_logout_button = (LinearLayout)rootView.findViewById(R.id.account_logout_button);
        btn_account_logout_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
             //logout
                //popup
                text.setVisibility(View.VISIBLE);
                text.setText(getResources().getString(R.string.component_log_sure));

                HGBUtility.showAlertPopUp(getActivity(), input, promptsView,
                        getResources().getString(R.string.component_log_out),getResources().getString(R.string.ok_button),
                        new PopUpAlertStringCB() {
                            @Override
                            public void itemSelected(String inputItem) {
                                getFlowInterface().gotToStartMenuActivity();
                            }

                            @Override
                            public void itemCanceled() {

                            }
                        });
            }

        });


        AccountSettingsAdapter accountSettingsAdapter = new AccountSettingsAdapter(account_settings,getActivity());
        account_settings_preferences_list.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //account_settings_preferences_list.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        account_settings_preferences_list.setLayoutManager(mLayoutManager);

        account_settings_preferences_list.setAdapter(accountSettingsAdapter);

        account_details_image = (RoundedImageView) rootView.findViewById(R.id.account_details_image);


        account_settings_details_name = (FontTextView) rootView.findViewById(R.id.account_settings_details_name);

        account_settings_details_city = (FontTextView) rootView.findViewById(R.id.account_settings_details_city);

        getUserData();
      //  initializeUserData();

        accountSettingsAdapter.SetOnItemClickListener(new AccountSettingsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                switch (position){
                    case 0:
                        getFlowInterface().goToFragment(ToolBarNavEnum.COMPANIONS_PERSONAL_DETAILS.getNavNumber(), null);
                        //AccountPersonalInfoSettingsFragment
                        //personal information
                        break;
               /*     case 1:
                        getFlowInterface().goToFragment(ToolBarNavEnum.PREFERENCE_SETTINGS_EMAILS.getNavNumber(), null);
                        //emails
                        break;*/
                    case 1:
                        getFlowInterface().goToFragment(ToolBarNavEnum.CREDIT_CARD_LIST.getNavNumber(), null);
                        break;
                    case 2:
                        //travel preferences
                        getFlowInterface().goToFragment(ToolBarNavEnum.TREVEL_PREFERENCE.getNavNumber(), null);
                        break;
                    case 3:
                        //membership
                        getFlowInterface().goToFragment(ToolBarNavEnum.PREFERENCES_MEMBERSHIP.getNavNumber(), null);
                        break;
                    case 4:
                        getFlowInterface().goToFragment(ToolBarNavEnum.COMPANION_HELP_FEEDBACK.getNavNumber(), null);
                        //help & feedback
                        break;
                }



            }
        });


     //   getFlowInterface().loadJSONFromAsset();
        //getCountries();


        account_details_image.setOnClickListener(imageClickListener);


        return rootView;
    }



   private View.OnClickListener imageClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            selectImage();
         /*   boolean permissionGainedStorage = HGBUtility.isStoragePermissionGranted(getActivity());
            boolean permissionGainedCamera = HGBUtility.isCameraPermissionGranted(getActivity());

            if(permissionGainedStorage && permissionGainedCamera){
                selectImage();
            }*/

        }
    };



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults[0]== PackageManager.PERMISSION_GRANTED ){
            selectImage();
        }
    }

    private void selectImage() {

        final CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setCancelable(false);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                boolean result= HGBUtilityPermissions.checkPermission(getActivity());


                if (items[item].equals("Take Photo")) {

                    userChoosenTask ="Take Photo";
                    if(result){
                        cameraIntent();
                    }

                /*    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);*/

                } else if (items[item].equals("Choose from Library")) {

                    userChoosenTask ="Choose from Library";
                    if(result) {
                        galleryIntent();
                    }

                   /* Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image*//*");

                    startActivityForResult(intent,
                            SELECT_FILE);*/

                   /* startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                           SELECT_FILE);*/
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
     //   startActivityForResult(intent, IntentIntegrator.REQUEST_CODE);
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }


    int REQUEST_CAMERA = 100;
    int SELECT_FILE = 101;


    private Bitmap compressBitmap(Bitmap thumbnail){
       // Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.PNG, 90, bytes);
        return  thumbnail;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }


    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        thumbnail=null;
        if (data != null) {
            try {
                thumbnail = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                byte[] b = bytes.toByteArray();
                String encodedString = Base64.encodeToString(b, Base64.DEFAULT);

                sendImageToServer(encodedString, currentUser.getUserprofileid());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        account_details_image.setImageBitmap(thumbnail);
    }

    private void onCaptureImageResult(Intent data) {
        thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        byte[] b = bytes.toByteArray();
        String encodedString = Base64.encodeToString(b, Base64.DEFAULT);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sendImageToServer(encodedString, currentUser.getUserprofileid());
        account_details_image.setImageBitmap(thumbnail);
    }

   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.PNG, 300, bytes);

                byte[] b = bytes.toByteArray();
                String encodedString = Base64.encodeToString(b, Base64.DEFAULT);

                sendImageToServer(encodedString, currentUser.getUserprofileid());


              //  Bitmap thumbnail2 = HGBUtility.getRoundedCornerBitmap(Bitmap.createScaledBitmap(thumbnail, HGBConstants.PROFILE_IMAGE_WIDTH, HGBConstants.PROFILE_IMAGE_HEIGHT, false), 90);

                File destination = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".png");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                account_details_image.setImageBitmap(thumbnail);
            } else if (requestCode == SELECT_FILE) {
                Uri selectedImage = data.getData();
                try {
                    Bitmap  yourSelectedImage  = decodeUri(selectedImage);
                 //   Bitmap thumbnail2 = HGBUtility.getRoundedCornerBitmap(Bitmap.createScaledBitmap(yourSelectedImage, HGBConstants.PROFILE_IMAGE_WIDTH, HGBConstants.PROFILE_IMAGE_HEIGHT, false), 90);
                    account_details_image.setImageBitmap(yourSelectedImage);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    //TODO check compress or can i put width height
                    yourSelectedImage.compress(Bitmap.CompressFormat.PNG, 90, baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();
                    String encodedString = Base64.encodeToString(b, Base64.DEFAULT);

                    sendImageToServer(encodedString, currentUser.getUserprofileid());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
    }*/

    private void sendImageToServer(String image, String userProfile){
        ConnectionManager.getInstance(getActivity()).postAvatar(userProfile, image,new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                getUserData();
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }

    /*
    downsample your image to avoid OutOfMemory errors
     */
    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {

        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(selectedImage), null, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 90;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE
                    || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(selectedImage), null, o2);

    }

    private void getCountries(){
        ConnectionManager.getInstance(getActivity()).getBookingOptions(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                //responceText.setText((String) data);
                BookingRequestVO bookingrequest = (BookingRequestVO)data;
                //getActivityInterface().setCountries(bookingrequest.getCountries());
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }

}
