package options;

import android.content.Context;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


/* Lembrete:
 *       Mudar esta classe para acessar o servidor quando ele estiver pronto e online.
 */

public class ServerConnection {

    public Context context;

    private JSONObject json;
    private JSONArray array;

    private URL url;
    private URLConnection con;
    private OutputStreamWriter streamWriter;

    //Testes---------------------------------------------------------------------//
    private String usuario, dados_usuario, conta, saldo, anotacao, lista_desejos;
    //---------------------------------------------------------------------------//

    public ServerConnection(@NotNull Context context) {
        this.context = context;

        //--------------------------------------------//
        _dados_testes_ teste = new _dados_testes_();
        usuario = teste.usuario();
        dados_usuario = teste.dados_usuario();
        conta = teste.conta();
        saldo = teste.saldo();
        anotacao = teste.anotacao();
        lista_desejos = teste.lista_desejos();

        json = new JSONObject();
        array = new JSONArray();
        //--------------------------------------------//

        /*
        try {
            url = new URL(context.getResources().getString(R.string.server_address));
        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
         */
    }

    private void connect() {
        try {
            con = url.openConnection();
            con.setDoOutput(true);
            streamWriter = new OutputStreamWriter(con.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String senderAction(String sender) {
        String str = null;
        try {
            str = String.format("&%s=%s", URLEncoder.encode("sender", "UTF-8"),
                    URLEncoder.encode(sender, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    //----------------------------------------------------------------------//
    //Usuario

    public String[] getUserData(String id) {
        String[] values = new String[6];
        try {
            json = new JSONObject(usuario);
            array = json.optJSONArray("usuario");
            JSONObject items;

            assert array != null;
            String[] saveData = new String[4];
            for (int x = 0; x < array.length(); x++) {
                items = array.getJSONObject(x);

                if (items.optInt("id_usuario") == Integer.parseInt(id)) {
                    saveData[0] = String.valueOf(items.optInt("id_usuario"));
                    saveData[1] = items.optString("nome");
                    saveData[2] = items.optString("senha");
                    saveData[3] = items.optString("email");
                }
            }

            json = new JSONObject(dados_usuario);
            array = json.optJSONArray("dados_usuario");

            assert array != null;
            for (int y = 0; y < array.length(); y++) {
                items = array.getJSONObject(y);

                if (items.optInt("id_usuario") == Integer.parseInt(id)) {

                    System.arraycopy(saveData, 0, values, 0, saveData.length);

                    values[4] = items.optString("sexo");
                    values[5] = items.optString("data_nascimento");
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return values;
    }

    public String makeLogin(String name, String pass) {
        String id = null;
        try {
            json = new JSONObject(usuario);
            array = json.optJSONArray("usuario");
            JSONObject items;

            if (array != null) {
                for (int x = 0; x < array.length(); x++) {
                    items = array.getJSONObject(x);

                    for (int y = 0; y < items.length(); y++) {
                        if (items.optString("nome").toLowerCase().trim().equals(name)
                                && items.optString("senha").toLowerCase().trim().equals(pass)) {
                            id = String.valueOf(items.optInt("id_usuario"));
                        }
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return id;
    }

    public String checkEmail(String email) {
        String id = null;
        try {
            json = new JSONObject(usuario);
            array = json.optJSONArray("usuario");
            JSONObject items;

            if (array != null) {
                for (int x = 0; x < array.length(); x++) {
                    items = array.getJSONObject(x);

                    if (items.optString("email").equals(email)) {
                        id = String.valueOf(items.optInt("id_usuario"));
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return id;
    }

    public boolean setUser(String[] data) {
        boolean response = false;
        try {
            String str = "{" +
                    "\"usuario\" : {" +
                    "\"nome\" : \"" + data[0] + "\"," +
                    "\"senha\" : \"" + data[1] + "\"," +
                    "\"email\" : \"" + data[2] + "\"" +
                    "}," +
                    "\"dados_usuario\" : {" +
                    "\"sexo\" : \"" + data[3] + "\"," +
                    "\"data_nascimento\" : \"" + data[4] + "\"" +
                    "}" +
                    "}";
            JSONObject input = new JSONObject(str);

            response = true;


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }

    public boolean changePassword(String email, String newPass) {
        boolean response = false;
        try {
            json = new JSONObject(usuario);
            array = json.optJSONArray("usuario");
            JSONObject items;

            if (array != null) {
                for (int x = 0; x < array.length(); x++) {
                    items = array.getJSONObject(x);

                    if (items.optString("email").equals(email)) {
                        String str = "{\"nova_senha\" : \"" + newPass + "\"}";
                        JSONObject input = new JSONObject(str);

                        response = true;

                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }

    public boolean changeUser(String[] data) {
        boolean response = false;
        try {
            json = new JSONObject(usuario);
            array = json.optJSONArray("usuario");
            JSONObject items;

            if (array != null) {
                for (int x = 0; x < array.length(); x++) {
                    items = array.getJSONObject(x);
                    if (items.optInt("id_usuario") == Integer.parseInt(data[5])) {
                        response = true;
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }

    //Capital

    //Pega o total ganho pelo usuario
    public double getRealTotal(String id) {
        double total = 0;
        try {
            json = new JSONObject(saldo);
            array = json.optJSONArray("saldo");
            JSONObject items;

            if (array != null) {
                for (int x = 0; x < array.length(); x++) {
                    items = array.getJSONObject(x);

                    if (items.optInt("id_usuario") == Integer.parseInt(id)) {
                        total += items.optDouble("valor_saldo");
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return total;
    }

    //Pega o total ganho pelo usuario descontando o valor das contas
    public double getTotal(String id) {
        double total = getRealTotal(id);
        try {
            json = new JSONObject(conta);
            array = json.optJSONArray("conta");
            JSONObject items;

            if (array != null) {
                for (int x = 0; x < array.length(); x++) {
                    items = array.getJSONObject(x);

                    if (items.optInt("id_usuario") == Integer.parseInt(id)) {
                        total -= items.optDouble("valor_conta");
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return total;
    }

    //Listas

    public String[][] getList(String sender, String id) {
        JSONObject items;
        String[][] values = null;
        try {
            switch (sender) {
                case "conta":

                    //
                    json = new JSONObject(conta);
                    array = json.optJSONArray("conta");
                    //
                    assert array != null;
                    values = new String[array.length()][4];
                    for (int x = 0; x < array.length(); x++) {
                        items = array.getJSONObject(x);

                        if (items.optInt("id_usuario") == Integer.parseInt(id)) {
                            values[x][0] = String.valueOf(items.optInt("id_conta"));
                            values[x][1] = items.optString("nome_conta");
                            values[x][2] = String.valueOf(items.optDouble("valor_conta"));
                            values[x][3] = items.optString("data");
                        }
                    }
                    break;

                case "lista_desejos":

                    //
                    json = new JSONObject(lista_desejos);
                    array = json.optJSONArray("lista_desejos");
                    //
                    assert array != null;
                    values = new String[array.length()][5];
                    for (int x = 0; x < array.length(); x++) {
                        items = array.getJSONObject(x);

                        if (items.optInt("id_usuario") == Integer.parseInt(id)) {
                            values[x][0] = String.valueOf(items.optInt("id_desejo"));
                            values[x][1] = items.optString("nome_desejo");
                            values[x][2] = String.valueOf(items.optDouble("valor_desejo"));
                            values[x][3] = String.valueOf(items.optInt("imagem"));
                            values[x][4] = items.optString("comentario");
                        }
                    }
                    break;

                case "anotacao":

                    //
                    json = new JSONObject(anotacao);
                    array = json.optJSONArray("anotacao");
                    //
                    assert array != null;
                    values = new String[array.length()][4];
                    for (int x = 0; x < array.length(); x++) {
                        items = array.getJSONObject(x);

                        if (items.optInt("id_usuario") == Integer.parseInt(id)) {
                            values[x][0] = String.valueOf(items.optInt("id_anot"));
                            values[x][1] = items.optString("nome_anot");
                            values[x][2] = items.optString("conteudo");
                            values[x][3] = items.optString("data_anot");
                        }
                    }
                    break;

                case "saldo":
                    //
                    json = new JSONObject(saldo);
                    array = json.optJSONArray("saldo");
                    //
                    assert array != null;
                    values = new String[array.length()][3];
                    for (int x = 0; x < array.length(); x++) {
                        items = array.getJSONObject(x);

                        if (items.optInt("id_usuario") == Integer.parseInt(id)) {
                            values[x][0] = String.valueOf(items.optInt("id_saldo"));
                            values[x][1] = items.optString("nome_saldo");
                            values[x][2] = items.optString("valor_saldo");
                        }
                    }
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return values;
    }

    public boolean deleteListItem(String sender, String id, String id_item){
        return true;
    }
    public boolean addListItem(String sender, String id, String[] data){
        return  true;
    }
    //----------------------------------------------------------------------//

    /
    public String[] getUserData(String id){
        String[] values = new String[6];
        StringBuilder request = new StringBuilder();
        try{
            connect();

            request.append(URLEncoder.encode("id_usuario", "UTF-8"))
                    .append("=").append(URLEncoder.encode(id, "UTF-8"));

            request.append(senderAction("getUserData"));

            streamWriter.write(request.toString());

            BufferedReader reader = new BufferedReader(
					new InputStreamReader(con.getInputStream()));

            while (reader.readLine() != null){
                JSONObject obj = new JSONObject(reader.readLine());
                JSONArray array = obj.optJSONArray("data");

                assert array != null;
                for(int x = 0; x < array.length(); x++){
                    JSONObject childNode = array.getJSONObject(x);

                    values[0] = childNode.optString("id_usuario");
                    values[1] = childNode.optString("nome");
                    values[2] = childNode.optString("senha");
                    values[3] = childNode.optString("email");
                    values[4] = childNode.optString("sexo");
                    values[5] = childNode.optString("data_nascimento");
                }
            }

        }catch(Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return values;
    }

    public String makeLogin(String name, String pass){
        String id = null;
        StringBuilder request = new StringBuilder();
        try{
            connect();

            request.append(URLEncoder.encode("nome", "UTF-8"))
                    .append("=").append(URLEncoder.encode(name, "UTF-8")).append("&");
            request.append(URLEncoder.encode("senha", "UTF-8"))
                    .append("=").append(URLEncoder.encode(pass, "UTF-8"));
            request.append(senderAction("makeLogin"));

            streamWriter.write(request.toString());

            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            while (reader.readLine() != null){
                JSONObject obj = new JSONObject(reader.readLine());
                JSONArray array = obj.optJSONArray("data");

                assert array != null;
                for (int x = 0; x < array.length(); x++) {
                    JSONObject childNode = array.getJSONObject(x);

                    id = childNode.optString("id_usuario");
                }
            }
        }catch(Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return id;
    }

    public String checkEmail(String email){
        String id = null;
        StringBuilder request = new StringBuilder();
        try{
            connect();

            request.append(URLEncoder.encode("email", "UTF-8"))
                    .append("=").append(URLEncoder.encode(email, "UTF-8")).append("&");
            request.append(senderAction("checkEmail"));

            streamWriter.write(request.toString());

            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            while (reader.readLine() != null){
                JSONObject obj = new JSONObject(reader.readLine());
                JSONArray array = obj.optJSONArray("data");

                assert array != null;
                for (int x = 0; x < array.length(); x++) {
                    JSONObject childNode = array.getJSONObject(x);

                    id = childNode.optString("id_usuario");
                }
            }

        }catch(Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return id;
    }

    public String setUser(String[] data){
        String response = null;
        StringBuilder request = new StringBuilder();
        try{
            connect();

            if(data[0] != null) {
                request.append(URLEncoder.encode("id_usuario", "UTF-8"))
                        .append("=").append(URLEncoder.encode(data[0], "UTF-8")).append("&");
            }

            String json = "{" +
                    "\"data\" : [{" +
                    "\"nome\" : \"" + data[1] + "\"," +
                    "\"senha\" : \"" + data[2] + "\"," +
                    "\"email\" : \"" + data[3] + "\"," +
                    "\"sexo\" : \"" + data[4] + "\"," +
                    "\"data_nascimento\" : \"" + data[5] + "\"" +
                    "}]" +
                    "}";

            request.append(URLEncoder.encode("data", "UTF-8"))
                    .append("=").append(URLEncoder.encode(json, "UTF-8"));
            request.append(senderAction("setUser"));

            streamWriter.write(request.toString());

            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            while (reader.readLine() != null){
                response = reader.readLine();
            }

        }catch(Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return response;
    }

    public boolean changePassword(String email, String newPass){
        boolean response = false;
        StringBuilder request = new StringBuilder();
        try{
            connect();

            request.append(URLEncoder.encode("email", "UTF-8"))
                    .append("=").append(URLEncoder.encode(email, "UTF-8")).append("&");

            request.append(URLEncoder.encode("senha", "UTF-8"))
                    .append("=").append(URLEncoder.encode(newPass, "UTF-8")).append("&");

            request.append(senderAction("changePassword"));

            streamWriter.write(request.toString());

            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            while (reader.readLine() != null){
                if(!reader.readLine().equals("false")) {
                    response = true;
                }
            }

        }catch(Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return response;
    }

    public double getRealTotal(String id){
        double total = 0;
        StringBuilder request = new StringBuilder();
        try{
            connect();

            request.append(URLEncoder.encode("id", "UTF-8"))
                    .append("=").append(URLEncoder.encode(email, "UTF-8")).append("&");

            request.append(URLEncoder.encode("saldo", "UTF-8"))
                    .append("=").append(URLEncoder.encode(newPass, "UTF-8")).append("&");

            request.append(senderAction("getTotal"));

            streamWriter.write(request.toString());

            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            while (reader.readLine() != null){
                if(!reader.readLine().equals("false")) {
                    total = reader.readLine();
                }
            }

        }catch(Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return total;
    }

    public double getTotal(String id){
        double total = getRealTotal(id);
        StringBuilder request = new StringBuilder();
        try{
            connect();

            request.append(URLEncoder.encode("id", "UTF-8"))
                    .append("=").append(URLEncoder.encode(email, "UTF-8")).append("&");

            request.append(URLEncoder.encode("saldo", "UTF-8"))
                    .append("=").append(URLEncoder.encode(newPass, "UTF-8")).append("&");

            request.append(senderAction("getTotal"));

            streamWriter.write(request.toString());

            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            while (reader.readLine() != null){
                if(!reader.readLine().equals("false")) {
                    total = reader.readLine();
                }
            }

        }catch(Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return total;
    }

    public String[][] getList(String sender, String id){
        String[][] values = null;
        StringBuilder request = new StringBuilder();
        try{
            connect();

            request.append(URLEncoder.encode("id_usuario", "UTF-8"))
                    .append("=").append(URLEncoder.encode(id, "UTF-8")).append("&");

            request.append(senderAction(sender));

            streamWriter.write(request.toString());

            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            while (reader.readLine() != null){
                JSONArray array = (new JSONObject(reader.readLine())).optJSONArray("data");

                assert array != null;
                values = new String[array.length()][5];
                for(int x = 0; x < array.length(); x++){
                    JSONObject childNode = array.getJSONObject(x);

                    switch (sender){
                        case "conta":
                            values[x][0] = childNode.optString("id_conta");
                            values[x][1] = childNode.optString("nome_conta");
                            values[x][2] = childNode.optString("valor_conta");
                            values[x][3] = childNode.optString("data");
                            break;

                        case "lista_desejos":
                            values[x][0] = childNode.optString("id_desejo");
                            values[x][1] = childNode.optString("nome_desejo");
                            values[x][2] = childNode.optString("valor_desejo");
                            values[x][3] = childNode.optString("imagem");
                            values[x][4] = childNode.optString("comentario");
                            break;

                        case "anotacao":
                            values[x][0] = childNode.optString("id_anot");
                            values[x][1] = childNode.optString("nome_anot");
                            values[x][2] = childNode.optString("conteudo");
                            values[x][3] = childNode.optString("data_anot");
                            break;
							
						case "saldo":
							values[x][0] = childNode.optString("id_saldo");
							values[x][1] = childNode.optString("nome_saldo");
							values[x][2] = childNode.optString("valor_saldo");
							break;
                    }
                }
            }

        }catch(Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return values;
    }

    public boolean addListItem(String sender, String id, String[] data) {
        boolean response = false;
        StringBuilder request = new StringBuilder();
        try{
            connect();

            if(data[0] == null){
                data[0] = "";
            }
            String json = "";
            switch (sender){
                case "conta":
                    json = "{" +
                            "\"conta\" : [{" +
                            "\"id_conta\" : " + data[0] + "," +
                            "\"id_usuario\" : " + data[1] + ","+
                            "\"nome_conta\" : \"" + data[2] + "\"," +
                            "\"valor_conta\" : " + data[3] + "," +
                            "\"data\" : \"" + data[4] + "\"" +
                            "}]" +
                            "}";
                    break;

                case "lista_desejos":
                    json = "{" +
                            "\"lista_desejos\" : [{" +
                            "\"id_desejo\" : " + data[0] + "," +
                            "\"id_usuario\" : " + data[1] + ","+
                            "\"nome_desejo\" : \"" + data[2] + "\"," +
                            "\"valor_desejo\" : " + data[3] + "," +
                            "\"imagem\" : " + data[4] + "," +
                            "\"comentario\" : \"" + data[5] + "\"" +
                            "}]" +
                            "}";
                    break;

                case "anotacao":
                    json = "{" +
                            "\"anotacao\" : [{" +
                            "\"id_anot\" : " + data[0] + "," +
                            "\"id_usuario\" : " + data[1] + ","+
                            "\"nome_anot\" : \"" + data[2] + "\"," +
                            "\"conteudo\" : \"" + data[3] + "\"," +
                            "\"data_anot\" : \"" + data[4] + "\"," +
                            "}]" +
                            "}";
                    break;
					
				case "saldo":
					json = "{" +
                            "\"saldo\" : [{" +
                            "\"id_saldo\" : " + data[0] + "," +
                            "\"id_usuario\" : " + data[1] + ","+
                            "\"nome_saldo\" : \"" + data[2] + "\"," +
                            "\"valor_saldo\" : " + data[3] +
                            "}]" +
                            "}";
					break;
            }
			
            request.append(URLEncoder.encode("data", "UTF-8")).append("=")
                    .append(URLEncoder.encode(json, "UTF-8"));

            request.append(senderAction("add_" + sender));

            streamWriter.write(request.toString());

            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            while (reader.readLine() != null){
                if(!reader.readLine().equals("false")) {
                    response = Boolean.getBoolean(reader.readLine());
                }
            }

        }catch(Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return response;
    }

    public boolean deleteListItem(String sender, String id, String id_item) {
        boolean response = false;
        StringBuilder request = new StringBuilder();
        try{
            connect();

            request.append(URLEncoder.encode("id_usuario", "UTF-8"))
                    .append("=").append(URLEncoder.encode(id, "UTF-8")).append("&");

            request.append(URLEncoder.encode("id_item", "UTF-8"))
                    .append("=").append(URLEncoder.encode(id_item, "UTF-8")).append("&");

            request.append(senderAction("del_" + sender));

            streamWriter.write(request.toString());

            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            while (reader.readLine() != null){
                if(!reader.readLine().equals("false")) {
                    response = Boolean.getBoolean(reader.readLine());
                }
            }

        }catch(Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return response;
    }
     */
}
