package options;

import com.LizCore.R;

/*TODO:
    Classe de testes
*/
class _dados_testes_ {

    String usuario(){
        return "{" +
                "\"usuario\" : [{" +
                "\"id_usuario\" : 0," +
                "\"nome\" : \"Leo\"," +
                "\"senha\" : \"9090\"," +
                "\"email\" : \"@moonlight.com\"" +
                "}]" +
                "}";
    }
    String dados_usuario(){
        return "{" +
                "\"dados_usuario\" : [{" +
                "\"id_dados\" : 0," +
                "\"id_usuario\" : 0," +
                "\"sexo\" : \"M\"," +
                "\"data_nascimento\" : \"21/09/02\"" +
                "}]" +
                "}";
    }
    String conta(){
        return "{" +
            "\"conta\" : [" +
            "{" +
            "\"id_conta\" : 1," +
            "\"id_usuario\" : 0," +
            "\"nome_conta\" : \"Luz\"," +
            "\"valor_conta\" : 120.00," +
            "\"data\" : \"31/07/20\"" +
            "}," +
            "{" +
            "\"id_conta\" : 2," +
            "\"id_usuario\" : 0," +
            "\"nome_conta\" : \"Água\"," +
            "\"valor_conta\" : 125.50," +
            "\"data\" : \"01/08/20\"" +
            "}," +
            "{" +
            "\"id_conta\" : 3," +
            "\"id_usuario\" : 0," +
            "\"nome_conta\" : \"Celular\"," +
            "\"valor_conta\" : 200.00," +
            "\"data\" : \"12/07/20\"" +
            "}" +
            "]" +
            "}";
    }
    String saldo(){
        return "{" +
                "\"saldo\" : [{" +
                "\"id_saldo\" : 1," +
                "\"id_usuario\" : 0," +
                "\"nome_saldo\" : \"Mercado X\"," +
                "\"valor_saldo\" : 2200.00" +
                "}]" +
                "}";
    }
    String anotacao(){
        String text = "\n" +
                "\t\t\t\t \\*O Mágico de Oz/" +
                "\n" +
                "  Dorothy vivia no meio das grandes pradarias do Kansas, com seu tio Henry," +
                "que cuidava de uma fazenda, e a tia Em, mulher dele. A casa em que eles moravam" +
                "era pequena, porque a madeira para a sua construção precisava ser trazida de" +
                "carroça desde muito longe. Eram quatro paredes, um chão e um teto, que formavam" +
                "uma única peça; e nesta peça ficavam um fogão a lenha com uma aparência bem " +
                "enferrujada, um armário para os pratos, uma mesa, três\n" +
                "ou quatro cadeiras e as camas.\n" +
                "\n" +
                "  O tio Henry e a tia Em ocupavam uma cama de casal num dos cantos, e Dorothy," +
                "uma cama menor em outro. A casa não tinha sótão e nem porão – tirante um buraco" +
                "não muito grande cavado na terra, que chamavam de abrigo de ciclone, onde a família" +
                "poderia se esconder para o caso de aparecer um desses imensos redemoinhos de vento," +
                "tão fortes que são capazes de esmagar qualquer casa ou construção que encontrem no " +
                "caminho. Ao abrigo se chegava por um alçapão que ficava no meio do piso da casa;" +
                "do alçapão descia uma escada até o abrigo estreito e escuro." +
                "Quando Dorothy chegava à porta de casa e olhava em volta, só via a pradaria cinzenta" +
                " de todos os lados.";

        return "{" +
                "\"anotacao\" : [" +
                "{" +
                "\"id_anot\" : 1," +
                "\"id_usuario\" : 0," +
                "\"nome_anot\" : \"Pagar o Tio\"," +
                "\"conteudo\" : \"Lembrar de pagar o tio Rogério os R$50 pela carreta emprestada\"," +
                "\"data_anot\" : \"01/07/20\"" +
                "}," +
                "{" +
                "\"id_anot\" : 2," +
                "\"id_usuario\" : 0," +
                "\"nome_anot\" : \"Ler: 'O Mágico de Oz'\"," +
                "\"conteudo\" : \"" + text + "\"," +
                "\"data_anot\" : \"07/07/20\"" +
                "}" +
                "]" +
                "}";
    }
    String lista_desejos(){
        return "{" +
                "\"lista_desejos\" : [" +
                "{" +
                "\"id_desejo\" : 1," +
                "\"id_usuario\" : 0," +
                "\"nome_desejo\" : \"Carro\"," +
                "\"valor_desejo\" : 5500.00," +
                "\"imagem\" : " + R.mipmap.image_test2 + "," +
                "\"comentario\" : \"Fiat vermelho\"" +
                "}," +
                "{" +
                "\"id_desejo\" : 2," +
                "\"id_usuario\" : 0," +
                "\"nome_desejo\" : \"Violino\"," +
                "\"valor_desejo\" : 299.00," +
                "\"imagem\" : " + R.mipmap.image_test + "," +
                "\"comentario\" : \"MARINOS 4/4 MV-44 Classic\"" +
                "}" +
                "]" +
                "}";
    }
}
