package options;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.LizCore.R;
import org.jetbrains.annotations.NotNull;

public class TextActions {

    private ServerConnection server;
    public Context context;

    public TextActions(@NotNull Context context) {
        this.context = context;
        server = new ServerConnection(context);
    }

    //Função que realiza o setOnTouchListener para mostrar a senha (password)
    @SuppressLint("ClickableViewAccessibility")
    public void onTouchPassword(@NotNull final EditText password) {

        password.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (password.getRight()
                        - password.getCompoundDrawables()[2].getBounds().width())) {

                    password.setTransformationMethod(HideReturnsTransformationMethod
                            .getInstance());
                    return true;
                } else {
                    password.setTransformationMethod(PasswordTransformationMethod
                            .getInstance());
                }
            }
            return false;
        });
    }

    //Muda o background de um EditText caso seu conteúdo não for informado
    public void setBackgroundInThreeSeconds(final EditText editText, final Drawable bgId) {
        new CountDownTimer(3000, 1000) {
            @Override
            public void onFinish() {
                editText.setBackground(bgId);
            }

            @Override
            public void onTick(long millisUntilFinished) {
                //Evento não utilizado.
            }
        }.start();
    }

    // Método que verifica se o campo EditText informado está vazio.
    @SuppressLint("ResourceAsColor")
    public boolean emptyEditText(@NotNull EditText editText) {

        if (editText.getText().toString().isEmpty()) {
            Toast.makeText(context, String.format("Campo obrigatório vazio: %s", editText.getHint()
                    .toString()), Toast.LENGTH_SHORT).show();

            editText.setBackgroundColor(context.getColor(R.color.red));
            setBackgroundInThreeSeconds(editText, context.getDrawable(R.drawable.bg_edit_text));
            return false;

        } else
            return true;
    }

    //Método de atualização do "ProgressBar" que mostra o saldo restante,
    // seguindo o layout "progress_circle.xml"
    @SuppressLint("SetTextI18n")
    public void setProgress(@NotNull View view, String id) {

        ProgressBar progressBar = view.findViewById(R.id.progress_leftover);
        TextView progressTotal = view.findViewById(R.id.total);
        TextView progressPercent = view.findViewById(R.id.percent);

        double leftover = server.getTotal(id);
        double salary = server.getRealTotal(id);

        int percent = (int) (leftover / salary * 100);

        progressBar.setProgress(percent);
        progressPercent.setText(percent + "%");

        progressTotal.setText(String.format("R$ %s", leftover));
        if (percent <= 20) {
            progressTotal.setTextColor(context.getColor(R.color.red));

        } else if (percent <= 50) {
            progressTotal.setTextColor(context.getColor(R.color.yellow));

        } else {
            progressTotal.setTextColor(context.getColor(R.color.black));
        }
    }
}
