package com.thoughtworks.mpermission;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import permissions.dispatcher.DeniedPermission;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;
import permissions.dispatcher.ShowsRationale;

import static com.thoughtworks.mpermission.Utils.ContactUtils.insertDummyContact;

@RuntimePermissions
public class MainActivityWithPermissionsDispatcher extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_with_permissions_dispatcher);
        MainActivityWithPermissionsDispatcherPermissionsDispatcher.insertContactWithCheck(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // delegate the permission handling to generated method
        MainActivityWithPermissionsDispatcherPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission(Manifest.permission.WRITE_CONTACTS)
    void insertContact() {
        insertDummyContact(this);
    }

    @ShowsRationale(Manifest.permission.WRITE_CONTACTS)
    void showRationaleForWriteContact() {
        Toast.makeText(this, "We WRITE_CONTACTS Permission to write contact", Toast.LENGTH_SHORT).show();
    }

    @DeniedPermission(Manifest.permission.WRITE_CONTACTS)
    void showDeniedForWriteContact() {
        Toast.makeText(this, "WRITE_CONTACTS Denied", Toast.LENGTH_SHORT).show();
    }
}
