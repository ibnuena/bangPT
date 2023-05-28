package com.example.bangpt;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MypageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MypageFragment extends Fragment {
    ViewGroup viewGroup;
    TextView name;

    //private Button btn_badge;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MypageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MypageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MypageFragment newInstance(String param1, String param2) {
        MypageFragment fragment = new MypageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_mypage, container, false);

        name = rootView.findViewById(R.id.name);
        Bundle bundle = getArguments();
        String userID = bundle.getString("userID");
        String userPass = bundle.getString("userPass");
        name.setText(userID);

        Button btnID = rootView.findViewById(R.id.btnID);
        btnID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyInfoActivity.class);
                intent.putExtra("userID", userID);
                intent.putExtra("userPass", userPass);
                startActivity(intent);
            }
        });
        Button btn_write = rootView.findViewById(R.id.btn_write);
        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyWriteActivity.class);
                startActivity(intent);
            }
        });

        Button btn_badge = rootView.findViewById(R.id.btn_badge);
        btn_badge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyChallActivity.class);
                startActivity(intent);
            }
        });

        Button btn_result = rootView.findViewById(R.id.btn_result);
        btn_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ExerciseResult2Activity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }
}