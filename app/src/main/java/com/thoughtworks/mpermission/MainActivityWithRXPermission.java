package com.thoughtworks.mpermission;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.tbruyelle.rxpermissions.RxPermissions;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

import static com.thoughtworks.mpermission.Utils.ContactUtils.insertDummyContact;

public class MainActivityWithRXPermission extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_with_rxpermission);

        final RxPermissions rxPermissions = RxPermissions.getInstance(this);
        rxPermissions.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_CONTACTS)
                .flatMap(new Func1<Boolean, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(Boolean should) {
                        if (should) {
                            // User already denied the permission, but didn't
                            // checked "never ask again".
                            showRationaleForWriteContact();
                        }
                        return rxPermissions.request(Manifest.permission.WRITE_CONTACTS);
                    }
                })
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

    void showRationaleForWriteContact() {
        Toast.makeText(this, "We WRITE_CONTACTS Permission to write contact", Toast.LENGTH_SHORT).show();
    }

    void showDeniedForWriteContact() {
        Toast.makeText(this, "WRITE_CONTACTS Denied", Toast.LENGTH_SHORT).show();
    }
}
