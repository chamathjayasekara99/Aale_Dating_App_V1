package com.example.aale.ui.send_email;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


import com.example.aale.databinding.FragmentSendEmailBinding;
import com.example.aale.model.Email;
import com.google.android.material.textfield.TextInputLayout;


public class SendEmailFragment extends Fragment {

    private FragmentSendEmailBinding binding;
    private EditText senderEmail;
    private EditText receiverEmail;
    private  EditText subject;
    private EditText message;
    private Button sendBtn;
    private   Email email;
    private TextInputLayout senderEmailLayout;
    private TextInputLayout receiverEmailLayout;
    private TextInputLayout subjectLayout;
    private TextInputLayout messageLayout;
    private SendEmailViewModel sendEmailViewModel;
    private NavController navController;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sendEmailViewModel = new ViewModelProvider(this).get( SendEmailViewModel.class);
        binding = FragmentSendEmailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //bind  ui elements
        senderEmail = binding.edtSenderEmail;
        receiverEmail=binding.edtReceiverEmail;
        subject=binding.edtSubject;
        message=binding.edtMessage;
        sendBtn=binding.btnSendMail;
        //bind layout
        senderEmailLayout= binding.edtSenderEmailLayout;
        receiverEmailLayout=binding.edtReceiverEmailLayout;
        subjectLayout=binding.edtSubjectLayout;
        messageLayout=binding.edtMessageLayout;

        email= new Email();



        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initialize nav controller
        navController = Navigation.findNavController(view);
        //get the arguments
        if(getArguments()!=null) {
            SendEmailFragmentArgs args = SendEmailFragmentArgs.fromBundle(getArguments());
            senderEmail.setText(args.getAdminEmailId());
            receiverEmail.setText(args.getEmailId());
        }



        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.setSender(senderEmail.getText().toString());
                email.setReceiver(receiverEmail.getText().toString());
                email.setMessage(message.getText().toString());
                email.setSubject(subject.getText().toString());

                if(isValidEmail(email)){
                    sendEmailViewModel.sendEmail(email).observe(getViewLifecycleOwner(),integer -> {
                        if(integer >-1){
                            Toast.makeText(getContext(),"Email Sent successfully",Toast.LENGTH_SHORT).show();
                            //navigate back to users UI
                            navController.navigate(SendEmailFragmentDirections.actionSendEmailFragmentToNavUsers());
                        }else{
                            Toast.makeText(getContext(),"Email Sent unsuccessful",Toast.LENGTH_SHORT).show();
                        }

                    });
                }else{
                        Toast.makeText(getContext(),"Please check the email inputs",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

            //set email values



    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    //validate email input field
    private boolean isValidEmail(Email email){
        String emailPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
        boolean flag=true;
        if(email.getSender().isEmpty()){
            senderEmailLayout.setError("Sender email cannot be empty");
            flag=false;
        }
        if(email.getReceiver().isEmpty()){
            receiverEmailLayout.setError("Receiver email cannot be empty");
            flag=false;
        }
        if(email.getMessage().isEmpty()){
           messageLayout.setError("Message field cannot be empty");
            flag=false;
        }
       if(email.getSubject().isEmpty()){
            subjectLayout.setError("Subject field cannot be empty");
           flag=false;
        }
        if(email.getSubject().length()>50) {
            subjectLayout.setError("Subject is too long");
            flag=false;
        }
       if(email.getMessage().length()>250) {
           messageLayout.setError("Message is too long");
           flag=false;
       }
        if(!email.getSender().matches(emailPattern)) {
            senderEmailLayout.setError("Invalid email");
            flag=false;
        }
        if(!email.getReceiver().matches(emailPattern)){
            receiverEmailLayout.setError("Invalid email");
            flag=false;

        }
        if(flag){
            //set errors
            receiverEmailLayout.setError(null);
            senderEmailLayout.setError(null);
            subjectLayout.setError(null);
            messageLayout.setError(null);
            //disable  errors
            receiverEmailLayout.setErrorEnabled(false);
            receiverEmailLayout.setErrorEnabled(false);
            receiverEmailLayout.setErrorEnabled(false);
            receiverEmailLayout.setErrorEnabled(false);

        }
        return  flag;
    }

}