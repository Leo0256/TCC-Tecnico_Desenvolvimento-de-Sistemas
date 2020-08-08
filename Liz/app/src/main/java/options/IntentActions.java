package options;

import android.app.Activity;
import android.content.Intent;
import android.widget.ImageView;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import java.util.Objects;

//Realiza a transição de Activities com ou sem mudança de imagem e transição de dados
public class IntentActions {


    //Com transição de imagem
    public void newIntent(Activity sender, Class destiny, ImageView image) {
        Intent intent = new Intent(sender, destiny);
        String transition = Objects.requireNonNull(ViewCompat.getTransitionName(image));

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(sender, image, transition);
        sender.startActivity(intent, options.toBundle());
    }

    //Com transferência de dados
    public void newIntent(Activity sender, Class destiny, String extra) {
        Intent intent = new Intent(sender, destiny);
        intent.putExtra("extra", extra);

        sender.startActivity(intent);
    }

    //Com transferência de vários dados
    public void newIntent(Activity sender, Class destiny, String[] extra) {
        Intent intent = new Intent(sender, destiny);
        for (int x = 0; x < extra.length; x++) {
            intent.putExtra("extra " + x, extra[x]);
        }

        sender.startActivity(intent);
    }

    //Com transição de imagem e transferência de dados
    public void newIntent(Activity sender, Class destiny, String extra, ImageView image) {
        Intent intent = new Intent(sender, destiny);
        String transition = Objects.requireNonNull(ViewCompat.getTransitionName(image));

        intent.putExtra("extra", extra);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(sender, image, transition);

        sender.startActivity(intent, options.toBundle());
    }

    //Apenas a transição de activities
    public void newIntent(Activity sender, Class destiny) {
        Intent intent = new Intent(sender, destiny);
        sender.startActivity(intent);
    }
}
