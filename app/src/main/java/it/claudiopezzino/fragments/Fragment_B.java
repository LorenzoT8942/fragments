package it.claudiopezzino.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.Objects;

public class Fragment_B extends Fragment implements View.OnClickListener, EditText.OnEditorActionListener{

    private Button btn_send_message_b;
    private EditText etMessageB;
    private TextView tv_incoming_messages_b;
    private FragmentBListener listener;

    /* --------------------------------------------------------------------------------------------------------------------------*/

    public interface FragmentBListener{
        void sendMessageToA(CharSequence text);
    }

    /* --------------------------------------------------------------------------------------------------------------------------*/

    static Fragment_B newInstance(CharSequence message){
        Fragment_B fragment = new Fragment_B();
        Bundle args = new Bundle();
        args.putCharSequence("message", message);
        fragment.setArguments(args);
        return fragment;
    }

    /* --------------------------------------------------------------------------------------------------------------------------*/

    private void setBundleInstance(){

        Bundle bundle = this.getArguments();
        if(bundle != null){
            updateOnTextView(Objects.requireNonNull(bundle).getString("message"));
        }
    }

    /* --------------------------------------------------------------------------------------------------------------------------*/

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_b, container, false);

        btn_send_message_b = v.findViewById(R.id.btn_send_message_b);
        etMessageB = v.findViewById(R.id.etMessageB);
        tv_incoming_messages_b = v.findViewById(R.id.tv_incoming_messages_b);

        btn_send_message_b.setOnClickListener(this);
        etMessageB.setOnEditorActionListener(this);

        setBundleInstance();

        return v;
    }

    /* --------------------------------------------------------------------------------------------------------------------------*/

    @Override
    public void onClick(View v) {

        if(v.getId() == btn_send_message_b.getId()){
            CharSequence message = etMessageB.getText().toString();
            if(listener != null){
                listener.sendMessageToA(message);
            }
        }
    }

    /* --------------------------------------------------------------------------------------------------------------------------*/

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId == EditorInfo.IME_ACTION_DONE){
            CharSequence message = etMessageB.getText().toString();
            listener.sendMessageToA(message);
        }
        return false;
    }

    /* --------------------------------------------------------------------------------------------------------------------------*/

    // siccome conviene sia privato il metodo, erigere un metodo setText che richiama questo metodo
    public void updateOnTextView(CharSequence message){

        if(tv_incoming_messages_b != null) {
            tv_incoming_messages_b.setText(message);
        }
    }

    /* --------------------------------------------------------------------------------------------------------------------------*/

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentBListener) {
            listener = (FragmentBListener) context;
        }else {
            throw new RuntimeException(context.toString() + " must implement FragmentBListener");
        }
    }

    /* --------------------------------------------------------------------------------------------------------------------------*/
    
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
