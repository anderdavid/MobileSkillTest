package Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Anderdavid on 07/08/2016.
 */
public class ShowDialogAddItem extends AlertDialog{
    String message;
    String title;
    Activity activity;
    AlertDialog.Builder alertDialog;

    public ShowDialogAddItem(Context context) {
        super(context);

    }

    public void setConfig(Activity mActivity,String title,String message){

        this.message =message;
        this.title = title;
        this.activity =mActivity;

        alertDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        alertDialog=new AlertDialog.Builder(activity);
        alertDialog.setTitle(title)
                .setMessage(message)

                   /* .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })*/
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
    }
}






