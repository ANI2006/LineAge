package com.app.lineage6.ui.settings;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.app.lineage6.R;
import com.app.lineage6.databinding.FragmentSettingsBinding;

import java.util.ArrayList;

public class SettingsFragment extends Fragment {
//    private RecyclerView recyclerView;
//    private RecyclerView.Adapter mAdapter;
//    private RecyclerView.LayoutManager layoutManager;
private ListView listView;
    private ContactsAdapter contactsAdapter;
    private ArrayList<ContactModel> contactModelArrayList;

    private FragmentSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        listView = (ListView) root.findViewById(R.id.listView);
        contactModelArrayList = new ArrayList<>();


        Cursor phones;
        phones = requireActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC");
        int nameIndex = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        int numberIndex = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

        while (phones.moveToNext())
        {
            String name = "";
            String phoneNumber = "";

            // Check if the column index is valid
            if (nameIndex >= 0) {
                name = phones.getString(nameIndex);
            }
            if (numberIndex >= 0) {
                phoneNumber = phones.getString(numberIndex);
            }

            ContactModel contactModel = new ContactModel();
            contactModel.setName(name);
            contactModel.setNumber(phoneNumber);
            contactModelArrayList.add(contactModel);
            Log.d("name>>",name+"  "+phoneNumber);
        }
        phones.close();
        contactsAdapter = new ContactsAdapter(getActivity(), contactModelArrayList);
        listView.setAdapter(contactsAdapter);



//        recyclerView = root.findViewById(R.id.my_recycler_view);
//        // use this setting to improve performance if you know that changes
//        // in content do not change the layout size of the RecyclerView
//        recyclerView.setHasFixedSize(true);
//        // use a linear layout manager
//        layoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(layoutManager);
//        List<String> input = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            input.add("Test" + i);
//        }// define an adapter
//        mAdapter = new ContactsAdapter(input);
//        recyclerView.setAdapter(mAdapter);

          return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}