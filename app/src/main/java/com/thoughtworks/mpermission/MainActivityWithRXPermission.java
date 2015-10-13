package com.thoughtworks.mpermission;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;

import static com.thoughtworks.mpermission.Utils.ContactUtils.insertDummyContact;

/**
 * No shouldShowRequestPermissionRationale support
 * https://github.com/tbruyelle/RxPermissions/issues/8
 */
public class MainActivityWithRXPermission extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_with_rxpermission);

        RxPermissions rxPermissions = RxPermissions.getInstance(this);
        rxPermissions
                .request(Manifest.permission.WRITE_CONTACTS)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (granted) { // Always true pre-M
                            insertDummyContact(MainActivityWithRXPermission.this);
                        } else {
                            // Oups permission denied
                            MainActivityWithRXPermission.this.showDeniedForWriteContact();
                        }
                    }
                });
    }

    void showDeniedForWriteContact() {
        Toast.makeText(this, "WRITE_CONTACTS Denied", Toast.LENGTH_SHORT).show();
    }
}
