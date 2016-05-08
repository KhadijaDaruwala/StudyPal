package com.studypal.khadija.studypal.Scribble;

import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.studypal.khadija.studypal.R;

import java.util.List;
public class ScribbleFragment extends Fragment {

    private ListView listView;
    private Button btnAdd;
    private DatabaseAccess databaseAccess;
    private List<Memo> memos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_scribble, container, false);

        setTitle();
        this.databaseAccess = DatabaseAccess.getInstance(getActivity());

        this.listView = (ListView) rootView.findViewById(R.id.listView);
        this.btnAdd = (Button)rootView.findViewById(R.id.btnAdd);

        this.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddClicked();
            }
        });

        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Memo memo = memos.get(position);
                TextView txtMemo = (TextView) view.findViewById(R.id.txtMemo);
                if (memo.isFullDisplayed()) {
                    txtMemo.setText(memo.getShortText());
                    memo.setFullDisplayed(false);
                } else {
                    txtMemo.setText(memo.getText());
                    memo.setFullDisplayed(true);
                }
            }
        });

        return rootView;
    }
    private void setTitle() {
        getActivity().setTitle("Scribble");
    }
    @Override
    public void onResume() {
        super.onResume();
        databaseAccess.open();
        this.memos = databaseAccess.getAllMemos();
        databaseAccess.close();
        MemoAdapter adapter = new MemoAdapter(getActivity(), memos);
        this.listView.setAdapter(adapter);
    }

    public void onAddClicked() {
        Intent intent = new Intent(getActivity(), EditActivity.class);
        startActivity(intent);
    }

    public void onDeleteClicked(Memo memo) {
        databaseAccess.open();
        databaseAccess.delete(memo);
        databaseAccess.close();

        ArrayAdapter<Memo> adapter = (ArrayAdapter<Memo>) listView.getAdapter();
        adapter.remove(memo);
        adapter.notifyDataSetChanged();
    }

    public void onEditClicked(Memo memo) {
        Intent intent = new Intent(getActivity(), EditActivity.class);
        intent.putExtra("MEMO", memo);
        startActivity(intent);
    }

    private class MemoAdapter extends ArrayAdapter<Memo> {


        public MemoAdapter(Context context, List<Memo> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.layout_list_item, parent, false);
            }

            ImageView btnEdit = (ImageView) convertView.findViewById(R.id.btnEdit);
            ImageView btnDelete = (ImageView) convertView.findViewById(R.id.btnDelete);
            TextView txtDate = (TextView) convertView.findViewById(R.id.txtDate);
            TextView txtMemo = (TextView) convertView.findViewById(R.id.txtMemo);

            final Memo memo = memos.get(position);
            memo.setFullDisplayed(false);
            txtDate.setText(memo.getDate());
            txtMemo.setText(memo.getShortText());
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onEditClicked(memo);
                }
            });
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteClicked(memo);
                }
            });
            return convertView;
        }
    }
}
