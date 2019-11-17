
import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class FragmentDialog extends DialogFragment {
    View.OnClickListener onClickListener;

    public FragmentDialog(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.exit_alert, container, false);
        // getDialog().setTitle("Exit");
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        TextView MESSAGE = (TextView)rootView.findViewById(R.id.msg);
        MESSAGE.setText(getTag());

        Button dismiss = (Button) rootView.findViewById(R.id.no);
        dismiss.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Button yes = (Button) rootView.findViewById(R.id.yes);
        yes.setOnClickListener(onClickListener);

        return rootView;
    }

}


/*
private FragmentDialog dialog;
dialog = new FragmentDialog(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               update();
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        dialog.show(getFragmentManager(), "Are you sure You want to Update ?");
*/
