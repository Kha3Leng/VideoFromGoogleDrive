package com.example.videofromgoogledrive;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DataGetterFragmentListener {

    private TabLayout tabLayout;
    private ViewPager pager;
    private ListView lv;

    private DatabaseHeloper db;

    Bundle data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("ALL");

        tabLayout = findViewById(R.id.tab);
        pager = findViewById(R.id.pager);


        db = new DatabaseHeloper(this);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(SearchViewListView.newInstance(), "Search & List");
        adapter.addFragment(new WebView().newInstance(), "Web View");
        adapter.addFragment(HostTwoFragments.newInstance(), "Fragments");

        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager, true);

        for (int i = 0; i < adapter.getCount(); i++) {
            tabLayout.getTabAt(i).setText(adapter.getTitle(i));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.insert:
                DialogFragment dialogFragment = DataDialogFragment.newInstance();
                dialogFragment.show(getSupportFragmentManager(), "dialog");
                break;
            case R.id.read:
                reading();
                break;
            case R.id.delete:
                DeleteFragment df = DeleteFragment.newInstance();
                df.show(getSupportFragmentManager(), "delete dialog");
                break;
            case R.id.update:
                UpdateFragment uf = UpdateFragment.newInstance();
                uf.show(getSupportFragmentManager(), "update dialog");
                break;


        }
        return true;
    }

    private void reading() {
        StringBuffer buffer = new StringBuffer();
        Cursor cur = db.allData();
        ArrayList<String> datas = new ArrayList<>();
        Bundle dummy = new Bundle();

        if (cur.getCount() == 0)
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();

        while (cur.moveToNext()) {
            buffer.append("Name\t\t:\t" + cur.getString(0) + "\n");
            buffer.append("Age\t\t:\t" + cur.getString(1) + "\n");
            buffer.append("ID\t\t:\t" + cur.getString(2) + "\n");
            dummy.putString("Name", cur.getString(0));
            dummy.putString("Age", cur.getString(1));
            dummy.putString("ID", cur.getString(2));
            datas.add("Name : " + cur.getString(0)
                    + ", Age : " + cur.getString(1)
                    + ", ID : " + cur.getString(2));
//            datas.add(dummy);
//            dummy.clear();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialog = inflater.inflate(R.layout.dbreturndata, null);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datas);
        lv = dialog.findViewById(R.id.returnData);
        lv.setAdapter(adapter);

        builder.setView(dialog)
                .setTitle("Results")
                .setCancelable(true)
                .show();
    }

    private void insertion(Bundle bundle) {
        boolean inserted = db.insertData(bundle);
        if (inserted == true)
            Toast.makeText(this, "Successfully added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getData(Bundle bundle) {
        if (bundle != null) {
            data = new Bundle(bundle);
            switch (data.getString("type")) {
                case "insert":
                    Toast.makeText(this, bundle.getString("name") + ":" + bundle.getInt("age"), Toast.LENGTH_SHORT).show();
                    insertion(data);
                    break;
                case "delete":
                    Toast.makeText(this, "deleting " + bundle.getString("ID"), Toast.LENGTH_SHORT).show();
                    if (!deletion(bundle.getString("ID")))
                        Toast.makeText(this, "doesnt have record with id " + bundle.getString("ID"), Toast.LENGTH_SHORT).show();
                    break;
                case "update":
                    Toast.makeText(this, "updating..", Toast.LENGTH_SHORT).show();
                    if (!updateData(bundle))
                        Toast.makeText(this, "doesn't have record with id", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    private boolean updateData(Bundle bundle){ return db.updateData(bundle);}

    private boolean deletion(String id) {
        return db.deleteData(id);
    }


    public static class UpdateFragment extends DialogFragment {

        DataGetterFragmentListener listener;

        @Override
        public void onAttach(@NonNull Context context) {
            super.onAttach(context);

            if (context instanceof DataGetterFragmentListener)
                listener = (DataGetterFragmentListener) context;
        }

        public static UpdateFragment newInstance(){
            return new UpdateFragment();
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View dialogU = inflater.inflate(R.layout.fragment_update, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setView(dialogU)
                    .setTitle("Update")
                    .setCancelable(true)
                    .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText name = dialogU.findViewById(R.id.name);
                            EditText age = dialogU.findViewById(R.id.age);
                            EditText id = dialogU.findViewById(R.id.idid);

                            Bundle bundle = new Bundle();
                            bundle.putString("name", name.getText().toString());
                            bundle.putInt("age", Integer.parseInt(age.getText().toString()));
                            bundle.putInt("id", Integer.parseInt(id.getText().toString()));
                            bundle.putString("type", "update");
                            listener.getData(bundle);
                        }
                    });
            return builder.create();
        }
    }

    public static class DeleteFragment extends DialogFragment {
        DataGetterFragmentListener listener;

        public DeleteFragment() {
        }

        public static DeleteFragment newInstance() {
            return new DeleteFragment();
        }

        @Override
        public void onAttach(@NonNull Context context) {
            super.onAttach(context);
            if (context instanceof DataGetterFragmentListener)
                listener = (DataGetterFragmentListener) context;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View dialogD = inflater.inflate(R.layout.delete_fragment, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setView(dialogD)
                    .setTitle("Delete data")
                    .setCancelable(true)
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText et = dialogD.findViewById(R.id.rec_id);
                            Bundle bundle = new Bundle();
                            bundle.putString("type", "delete");
                            bundle.putString("ID", et.getText().toString());
                            listener.getData(bundle);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            return builder.create();

        }
    }

    public static class DataDialogFragment extends DialogFragment {
        EditText text;
        DataGetterFragmentListener listener;

        public static DataDialogFragment newInstance() {
            return new DataDialogFragment();
        }

        @Override
        public void onAttach(@NonNull Context context) {
            super.onAttach(context);

            if (context instanceof DataGetterFragmentListener) {
                listener = (DataGetterFragmentListener) context;
            }
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View dialogv = inflater.inflate(R.layout.insert, null);
            text = dialogv.findViewById(R.id.name);

            builder.setView(dialogv)
                    .setTitle("Insert Data")
                    .setMessage("Insert name and age")
                    .setPositiveButton("Insert", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getContext(), "You click inserted", Toast.LENGTH_SHORT).show();
                            Bundle bundle = new Bundle();
                            EditText name = dialogv.findViewById(R.id.name);
                            EditText age = dialogv.findViewById(R.id.age);
                            bundle.putString("name", name.getText().toString());
                            bundle.putInt("age", Integer.parseInt(age.getText().toString()));
                            bundle.putString("type", "insert");
                            listener.getData(bundle);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getContext(), "You click cancel", Toast.LENGTH_SHORT).show();
                        }
                    });

            return builder.create();
        }
    }

    static class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private static final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> titleList = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return (fragmentList == null) ? 0 : fragmentList.size();
        }

        public String getTitle(int pos) {
            return titleList.get(pos);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            titleList.add(title);
        }
    }
}