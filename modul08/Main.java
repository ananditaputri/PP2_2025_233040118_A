package id.ac.unpas.modul08;

import id.ac.unpas.modul08.controller.PersegiPanjangController;
import id.ac.unpas.modul08.model.PersegiPanjangModel;
import id.ac.unpas.modul08.view.PersegiPanjangView;

public class Main {
    public static void main(String[] args) {

        // 1. Instansiasi model
        PersegiPanjangModel model = new PersegiPanjangModel();

        // 2. Instansiasi view
        PersegiPanjangView view = new PersegiPanjangView();

        // 3. Hubungkan model & view melalui Controller
        PersegiPanjangController controller = new PersegiPanjangController(model, view);

        // 4. Tampilkan View
        view.setVisible(true);
    }
}
