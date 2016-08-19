package Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Anderdavid on 07/08/2016.
 */
public class ShowDialog extends DialogFragment {
    String message;
    String title;

    public void setConfig(FragmentTransaction ft,String title,String message){

        this.message =message;
        this.title = title;

        this.show(ft, "tag");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {



         return new AlertDialog.Builder(getActivity())
                    .setTitle(title)
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
