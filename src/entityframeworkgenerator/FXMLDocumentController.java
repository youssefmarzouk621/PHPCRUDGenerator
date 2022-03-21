/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityframeworkgenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ASUS_ROG
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Button generate;
    @FXML
    private TextField name;
    @FXML
    private TextField length;
    @FXML
    private TextArea code;
    @FXML
    private Button preview;
    @FXML
    private TextArea columns;
    @FXML
    private Button previewcore;

    private String resultModel = "";
    private boolean previewed = false;
    private String resulCore = "";
    private String resultAdd = "";
    private String resultDelete = "";
    private String resultAddUI = "";
    private String resultDisplayUI = "";
    private String resultUpdateUI = "";
    private String resultUpdate = "";

    private String projectDir = "";
    private String ModelsDir = "Models";
    private String ControllersDir = "Controllers";
    private String ViewsDir = "Backoffice";
    @FXML
    private TextField projectDirInput;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        resultModel = "<?PHP\n" + "class myclass{\n" + "\n" + "	//variables\n" + "\n" + "	function __construct(){\n" + "\n" + "	}\n" + "\n" + "	\n" + "}\n" + "\n" + "?>";
        code.setText(resultModel);

    }

    @FXML
    private void generateAction(ActionEvent event) {
        projectDir = projectDirInput.getText();
        if (projectDir.length() == 0) {
            System.out.println("specify your work directory");
        } else if ((previewed == false) || (resulCore == "") || (resultAdd == "") || (resultDelete == "")) {
            System.out.println("preview all views first");
        } else {
            String classname = name.getText();
            try {
                File Model = new File(projectDir + "\\" + ModelsDir + "\\" + classname + ".php");
                File Controller = new File(projectDir + "\\" + ControllersDir + "\\" + classname + "Controller.php");

                File classnameDirectory = new File(projectDir + "\\" + ViewsDir + "\\" + capitalize(classname) + "\\");
                classnameDirectory.mkdir();
                File AddService = new File(projectDir + "\\" + ViewsDir + "\\" + capitalize(classname) + "\\Add" + capitalize(classname) + ".php");
                File DeleteService = new File(projectDir + "\\" + ViewsDir + "\\" + capitalize(classname) + "\\Delete" + capitalize(classname) + ".php");
                File UpdateService = new File(projectDir + "\\" + ViewsDir + "\\" + capitalize(classname) + "\\Update" + capitalize(classname) + ".php");

                File DisplayUI = new File(projectDir + "\\" + ViewsDir + "\\" + capitalize(classname) + "\\index.php");
                File AddUI = new File(projectDir + "\\" + ViewsDir + "\\" + capitalize(classname) + "\\Add.php");
                File UpdateUI = new File(projectDir + "\\" + ViewsDir + "\\" + capitalize(classname) + "\\Update.php");

                if (Model.createNewFile()
                        && Controller.createNewFile()
                        && AddService.createNewFile()
                        && DeleteService.createNewFile()
                        && UpdateService.createNewFile()
                        && DisplayUI.createNewFile()
                        && AddUI.createNewFile()
                        && UpdateUI.createNewFile()) {

                    FileWriter WriteModel = new FileWriter(projectDir + "\\" + ModelsDir + "\\" + classname + ".php");
                    WriteModel.write(resultModel);
                    WriteModel.close();

                    FileWriter WriteController = new FileWriter(projectDir + "\\" + ControllersDir + "\\" + classname + "Controller.php");
                    WriteController.write(resulCore);
                    WriteController.close();

                    FileWriter WriteAddService = new FileWriter(projectDir + "\\" + ViewsDir + "\\" + capitalize(classname) + "\\Add" + capitalize(classname) + ".php");
                    WriteAddService.write(resultAdd);
                    WriteAddService.close();

                    FileWriter WriteDeleteService = new FileWriter(projectDir + "\\" + ViewsDir + "\\" + capitalize(classname) + "\\Delete" + capitalize(classname) + ".php");
                    WriteDeleteService.write(resultDelete);
                    WriteDeleteService.close();

                    FileWriter WriteUpdateService = new FileWriter(projectDir + "\\" + ViewsDir + "\\" + capitalize(classname) + "\\Update" + capitalize(classname) + ".php");
                    WriteUpdateService.write(resultUpdate);
                    WriteUpdateService.close();

                    FileWriter WriteAddUI = new FileWriter(projectDir + "\\" + ViewsDir + "\\" + capitalize(classname) + "\\Add.php");
                    WriteAddUI.write(resultAddUI);
                    WriteAddUI.close();

                    FileWriter WriteDisplayUI = new FileWriter(projectDir + "\\" + ViewsDir + "\\" + capitalize(classname) + "\\index.php");
                    WriteDisplayUI.write(resultDisplayUI);
                    WriteDisplayUI.close();

                    FileWriter WriteUpdateUI = new FileWriter(projectDir + "\\" + ViewsDir + "\\" + capitalize(classname) + "\\Update.php");
                    WriteUpdateUI.write(resultUpdateUI);
                    WriteUpdateUI.close();

                    System.out.println("Model created: " + Model.getName());
                    System.out.println("Controller created: " + Controller.getName());

                    System.out.println("AddService created: " + AddService.getName());
                    System.out.println("DeleteService created: " + DeleteService.getName());
                    System.out.println("UpdateService created: " + UpdateService.getName());

                    System.out.println("AddUI created: " + AddUI.getName());
                    System.out.println("DisplayUI created: " + DisplayUI.getName());
                    System.out.println("UpdateUI created: " + UpdateUI.getName());

                } else {
                    System.out.println("Files already exists.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    private void previewAction(ActionEvent event) {
        previewed = true;
        String classname = name.getText();
        String[] arrayofvariables = columns.getText().split("\\r?\\n");
        length.setText(arrayofvariables.length + "");
        String variables = "";
        String constructvariables = "";
        String constructcontent = "";
        String getters = "";
        String setters = "";
        for (int i = 0; i < arrayofvariables.length; i++) {
            variables += "private $" + arrayofvariables[i] + ";\n\t";
            if (i == arrayofvariables.length - 1) {
                constructvariables += "$" + arrayofvariables[i];
            } else {
                constructvariables += "$" + arrayofvariables[i] + ",";
            }
            constructcontent += "$this->" + arrayofvariables[i] + "=$" + arrayofvariables[i] + ";\n\t";
            getters += "	function get" + arrayofvariables[i] + "(){\n" + "		return $this->" + arrayofvariables[i] + ";\n" + "	}\n";
            setters += "	function set" + arrayofvariables[i] + "($" + arrayofvariables[i] + "){\n" + "		$this->" + arrayofvariables[i] + "=$" + arrayofvariables[i] + ";\n" + "	}\n";

        }
        resultModel = "<?PHP\n" + "class " + classname + "{\n" + "\n" + "	" + variables + "\n" + "\n" + "	function __construct(" + constructvariables + "){\n\t" + constructcontent + "	}\n" + getters + "\n" + setters + "	\n" + "}\n" + "\n" + "?>";

        code.setText(resultModel);
    }

    @FXML
    private void previewCoreAction(ActionEvent event) {
        String classname = name.getText();
        String[] arrayofvariables = columns.getText().split("\\r?\\n");
        length.setText(arrayofvariables.length + "");
        String constructvariables = "";
        String bindValuesql = "";
        String affectvalues = "";
        String bindvalues = "";
        String updatesql = "";
        for (int i = 1; i < arrayofvariables.length; i++) {
            if (i == arrayofvariables.length - 1) {
                constructvariables += arrayofvariables[i];
                bindValuesql += ":" + arrayofvariables[i];
                updatesql += arrayofvariables[i] + "=:" + arrayofvariables[i];
            } else {
                constructvariables += arrayofvariables[i] + ",";
                bindValuesql += ":" + arrayofvariables[i] + ",";
                updatesql += arrayofvariables[i] + "=:" + arrayofvariables[i] + ",";
            }
            affectvalues += "$" + arrayofvariables[i] + "=$" + classname + "->get" + arrayofvariables[i] + "();\n\t";
            bindvalues += "$req->bindValue(':" + arrayofvariables[i] + "',$" + arrayofvariables[i] + ");\n\t";
        }
        resulCore = "<?PHP\n"
                + "class " + classname + "Controller {\n"
                + "\n"
                + "	function ajouter" + classname + "($" + classname + "){\n"
                + "		$sql=\"INSERT INTO " + classname + " (" + constructvariables + ") VALUES (" + bindValuesql + ")\";\n"
                + "		$db = config::getConnexion();\n"
                + "		try{\n"
                + "        $req=$db->prepare($sql);\n"
                + "	\n"
                + "\n"
                + "        " + affectvalues
                + "\n"
                + "\n"
                + "	" + bindvalues
                + "		\n"
                + "            $req->execute();\n"
                + "           \n"
                + "        }\n"
                + "        catch (Exception $e){\n"
                + "            echo 'Erreur: '.$e->getMessage();\n"
                + "        }\n"
                + "		\n"
                + "	}\n"
                + "	\n"
                + "	function afficher" + classname + "(){\n"
                + "\n"
                + "		$sql=\"SELECT * FROM " + classname + "\";\n"
                + "		$db = config::getConnexion();\n"
                + "		try{\n"
                + "		$liste=$db->query($sql);\n"
                + "		return $liste;\n"
                + "		}\n"
                + "        catch (Exception $e){\n"
                + "            die('Erreur: '.$e->getMessage());\n"
                + "        }	\n"
                + "	}\n"
                + "	function supprimer" + classname + "($" + arrayofvariables[0] + "){\n"
                + "		$sql=\"DELETE FROM " + classname + " where " + arrayofvariables[0] + "= :" + arrayofvariables[0] + "\";\n"
                + "		$db = config::getConnexion();\n"
                + "        $req=$db->prepare($sql);\n"
                + "		$req->bindValue(':" + arrayofvariables[0] + "',$" + arrayofvariables[0] + ");\n"
                + "		try{\n"
                + "            $req->execute();\n"
                + "           // header('Location: index.php');\n"
                + "        }\n"
                + "        catch (Exception $e){\n"
                + "            die('Erreur: '.$e->getMessage());\n"
                + "        }\n"
                + "	}\n"
                + "	function modifier" + classname + "($" + classname + ",$" + arrayofvariables[0] + "){\n"
                + "		$sql=\"UPDATE " + classname + " SET " + updatesql + " WHERE " + arrayofvariables[0] + "=:" + arrayofvariables[0] + "\";\n"
                + "		$db = config::getConnexion();\n"
                + "try{		\n"
                + "        $req=$db->prepare($sql);\n"
                + "\n"
                + "		" + affectvalues
                + "\n"
                + "		$req->bindValue(':" + arrayofvariables[0] + "',$" + arrayofvariables[0] + ");\n"
                + "\n"
                + "		" + bindvalues
                + "\n"
                + "        $s=$req->execute();\n"
                + "			\n"
                + "           // header('Location: index.php');\n"
                + "        }\n"
                + "        catch (Exception $e){\n"
                + "            echo \" Erreur ! \".$e->getMessage();\n"
                + "        }\n"
                + "		\n"
                + "	}\n"
                + "	function recuperer" + classname + "($" + arrayofvariables[0] + "){\n"
                + "\n"
                + "		$sql=\"SELECT * from " + classname + " where " + arrayofvariables[0] + "=$" + arrayofvariables[0] + "\";\n"
                + "		$db = config::getConnexion();\n"
                + "\n"
                + "		try{\n"
                + "		$liste=$db->query($sql);\n"
                + "		return $liste->fetch();;\n"
                + "		}\n"
                + "        catch (Exception $e){\n"
                + "            die('Erreur: '.$e->getMessage());\n"
                + "        }\n"
                + "	}\n"
                + "\n"
                + "	\n"
                + "\n"
                + "\n"
                + "}\n"
                + "\n"
                + "\n"
                + "\n"
                + "\n"
                + "\n"
                + "?>";

        code.setText(resulCore);
    }

    @FXML
    private void previewAddAction(ActionEvent event) {
        String classname = name.getText();
        String[] arrayofvariables = columns.getText().split("\\r?\\n");
        length.setText(arrayofvariables.length + "");
        String ifstatement = "";
        String construct = "0,";
        for (int i = 1; i < arrayofvariables.length; i++) {
            if (i == arrayofvariables.length - 1) {
                ifstatement += "isset($_POST['" + arrayofvariables[i] + "'])";
                construct += "$_POST['" + arrayofvariables[i] + "']";
            } else {
                ifstatement += "isset($_POST['" + arrayofvariables[i] + "']) and ";
                construct += "$_POST['" + arrayofvariables[i] + "'],";
            }
        }

        resultAdd = "<?PHP\n"
                + "	include \"../../config.php\";\n"
                + "	include \"../../" + ModelsDir + "/" + classname + ".php\";\n"
                + "	include \"../../" + ControllersDir + "/" + classname + "Controller.php\";\n"
                + "\n"
                + "if (" + ifstatement + ")\n"
                + "{\n"
                + "	  $" + classname + "=new " + classname + "(" + construct + ");\n"
                + "\n"
                + "		$" + classname + "Controller=new " + classname + "Controller();\n"
                + "		$" + classname + "Controller->ajouter" + classname + "($" + classname + ");\n"
                + "\n"
                + "		header('Location: index.php');\n"
                + "		//ob_end_clean();\n"
                + "	\n"
                + "}else{\n"
                + "	echo \"vérifier les champs\";\n"
                + "}\n"
                + "//*/\n"
                + "\n"
                + "?>";

        code.setText(resultAdd);
    }

    @FXML
    private void previewdeleteAction(ActionEvent event) {
        String classname = name.getText();

        String[] arrayofvariables = columns.getText().split("\\r?\\n");
        length.setText(arrayofvariables.length + "");
        resultDelete = "<?PHP\n"
                + "include \"../../config.php\";\n"
                + "include \"../../" + ControllersDir + "/" + classname + "Controller.php\";\n"
                + "$" + classname + "Controller = new " + classname + "Controller();\n"
                + "if (isset($_GET[\"" + arrayofvariables[0] + "\"])){\n"
                + "	$" + classname + "Controller->supprimer" + classname + "($_GET[\"" + arrayofvariables[0] + "\"]);\n"
                + "	header('Location: index.php');\n"
                + "}\n"
                + "?>";

        code.setText(resultDelete);
    }

    @FXML
    private void previewAddUIAction(ActionEvent event) {

        String classname = name.getText();

        String[] arrayofvariables = columns.getText().split("\\r?\\n");
        length.setText(arrayofvariables.length + "");
        String Inputs = "";
        for (int i = 0; i < arrayofvariables.length; i++) {
            if (i != 0) {
                Inputs += "                                        <div class=\"row\">\n"
                        + "                                            <div class=\"col-12\">\n"
                        + "                                                <div class=\"form-group\">\n"
                        + "                                                    <label for=\"" + arrayofvariables[i] + "\">" + capitalize(arrayofvariables[i]) + "</label>\n"
                        + "                                                    <div class=\"position-relative has-icon-left\">\n"
                        + "                                                        <input type=\"text\" id=\"" + arrayofvariables[i] + "\" class=\"form-control\"\n"
                        + "                                                            placeholder=\"" + capitalize(arrayofvariables[i]) + "\" name=\"" + arrayofvariables[i] + "\">\n"
                        + "                                                        <div class=\"form-control-position\">\n"
                        + "                                                            <i class=\"ft-user\"></i>\n"
                        + "                                                        </div>\n"
                        + "                                                    </div>\n"
                        + "                                                </div>\n"
                        + "                                            </div>\n"
                        + "                                        </div>\n \n";
            }

        }

        resultAddUI = "<!DOCTYPE html>\n"
                + "<html class=\"loading\" lang=\"en\" data-textdirection=\"ltr\">\n"
                + "\n"
                + "<head>\n"
                + "    <?php include(\"../_layouts/libs.php\") ?>\n"
                + "</head>\n"
                + "\n"
                + "\n"
                + "<body class=\"vertical-layout vertical-menu 2-columns   fixed-navbar\" data-open=\"click\" data-menu=\"vertical-menu\"\n"
                + "    data-color=\"bg-gradient-x-purple-blue\" data-col=\"2-columns\">\n"
                + "\n"
                + "    <?php include(\"../_layouts/header.php\") ?>\n"
                + "\n"
                + "    <!-- BEGIN: Content-->\n"
                + "    <div class=\"app-content content\">\n"
                + "        <div class=\"content-wrapper\">\n"
                + "            <div style=\"height:0px;\" class=\"content-wrapper-before\"></div>\n"
                + "            <div class=\"content-header row\">\n"
                + "            </div>\n"
                + "            <div class=\"content-body\">\n"
                + "\n"
                + "\n"
                + "\n"
                + "                <div class=\"col-12\">\n"
                + "                    <div class=\"card\">\n"
                + "                        <div class=\"card-header\">\n"
                + "                            <h4 class=\"card-title\" id=\"basic-layout-icons\">Ajouter</h4>\n"
                + "                            <a class=\"heading-elements-toggle\">\n"
                + "                                <i class=\"la la-ellipsis-v font-medium-3\"></i>\n"
                + "                            </a>\n"
                + "                            \n"
                + "                        </div>\n"
                + "                        <div class=\"card-content collapse show\">\n"
                + "                            <div class=\"card-body\">\n"
                + "\n"
                + "                                \n"
                + "\n"
                + "                                <form method=\"POST\" action=\"Add" + capitalize(classname) + ".php\" class=\"form\">\n"
                + "                                    <div class=\"form-body\">\n"
                + "\n"
                + "                                        \n"
                + Inputs
                + "\n"
                + "\n"
                + "\n"
                + "\n"
                + "\n"
                + "                                        \n"
                + "\n"
                + "                                    </div>\n"
                + "\n"
                + "                                    <div class=\"form-actions right\">\n"
                + "                                        <a href=\"index.php\" class=\"btn btn-danger mr-1\">\n"
                + "                                            <i class=\"ft-x\"></i> Cancel\n"
                + "                                        </a>\n"
                + "                                        <button type=\"submit\" class=\"btn btn-primary\">\n"
                + "                                            <i class=\"la la-check-square-o\"></i> Save\n"
                + "                                        </button>\n"
                + "                                    </div>\n"
                + "                                </form>\n"
                + "\n"
                + "                            </div>\n"
                + "                        </div>\n"
                + "                    </div>\n"
                + "                </div>\n"
                + "            </div>\n"
                + "        </div>\n"
                + "    </div>\n"
                + "\n"
                + "\n"
                + "\n"
                + "    <!-- END: Content-->\n"
                + "\n"
                + "\n"
                + "\n"
                + "    <?php include(\"../_layouts/scripts.php\") ?>\n"
                + "\n"
                + "</body>\n"
                + "<!-- END: Body-->\n"
                + "\n"
                + "\n"
                + "</html>";

        code.setText(resultAddUI);
    }

    @FXML
    private void previewDisplayUIAction(ActionEvent event) {
        String classname = name.getText();

        String[] arrayofvariables = columns.getText().split("\\r?\\n");
        length.setText(arrayofvariables.length + "");
        String tableHeads = "";
        String tableColumns = "";

        for (int i = 0; i < arrayofvariables.length; i++) {
            tableHeads += "                         <th class=\"border-top-0\">" + arrayofvariables[i] + "</th>\n";

            tableColumns += "                        <td>\n"
                    + "                          <?php echo $" + classname + "['" + arrayofvariables[i] + "']; ?>\n"
                    + "                        </td>\n";
        }
        resultDisplayUI = "<!DOCTYPE html>\n"
                + "<html class=\"loading\" lang=\"en\" data-textdirection=\"ltr\">\n"
                + "\n"
                + "<head>\n"
                + "  <?php include(\"../_layouts/libs.php\") ?>\n"
                + "</head>\n"
                + "\n"
                + "\n"
                + "<body class=\"vertical-layout vertical-menu 2-columns   fixed-navbar\" data-open=\"click\" data-menu=\"vertical-menu\"\n"
                + "  data-color=\"bg-gradient-x-purple-blue\" data-col=\"2-columns\">\n"
                + "\n"
                + "  <?php include(\"../_layouts/header.php\") ?>\n"
                + "\n"
                + "  <!-- BEGIN: Content-->\n"
                + "  <div class=\"app-content content\">\n"
                + "    <div class=\"content-wrapper\">\n"
                + "      <div style=\"height:0px;\" class=\"content-wrapper-before\"></div>\n"
                + "      <div class=\"content-header row\">\n"
                + "      </div>\n"
                + "      <div class=\"content-body\">\n"
                + "        <div class=\"col-12\">\n"
                + "          <div style=\"margin-bottom: 10px;\" class=\"row\">\n"
                + "            <div class=\"col-6\">\n"
                + "            <h5 style=\"margin: 10px 0px 10px 0px!important;\" class=\"card-title text-bold-700 my-2\">" + capitalize(classname) + "</h5>\n"
                + "            </div>\n"
                + "            <div class=\"col-6\">\n"
                + "            <a href=\"Add.php\" style=\"text-decoration: none;color: white;float: right;\" class=\"btn btn-primary\">Ajouter</a>\n"
                + "            </div>\n"
                + "          </div>\n"
                + "          "
                + "          <div class=\"card\">\n"
                + "            <div class=\"card-content\">\n"
                + "              <div id=\"recent-projects\" class=\"media-list position-relative\">\n"
                + "                <div class=\"table-responsive\">\n"
                + "\n"
                + "\n"
                + "                  <?php \n"
                + "                    include(\"../../config.php\");\n"
                + "                    include(\"../../Controllers/" + classname + "Controller.php\");\n"
                + "                    $" + classname + "Controller = new " + classname + "Controller();\n"
                + "                    $" + classname + "s = $" + classname + "Controller->afficher" + classname + "();\n"
                + "                    ?>\n"
                + "                  <table class=\"table table-padded table-xl mb-0\" id=\"recent-project-table\">\n"
                + "                    <thead>\n"
                + "                      <tr>\n"
                + tableHeads
                + "                         <th class=\"border-top-0\">Actions</th>\n"
                + "                      </tr>\n"
                + "                    </thead>\n"
                + "                    <tbody>\n"
                + "\n"
                + "                      <?php foreach ($" + classname + "s as $" + classname + "): ?>\n"
                + "                      <tr>\n"
                + tableColumns
                + "                        <td style=\"padding: 9px;\">\n"
                + "                          <a style=\"padding: 9px;\" class=\"btn btn-primary\" href=\"Update.php?id=<?php echo($" + classname + "['id']); ?>\"> Modifier</a>\n"
                + "                          <button onclick=\"confirmDelete(<?php echo($" + classname + "['id']); ?>)\" style=\"padding: 9px;\"\n"
                + "                            class=\"btn btn-danger\"> Supprimer</button>"
                + "                        </td>\n"
                + "                      </tr>\n"
                + "                      <?php endforeach ?>\n"
                + "                    </tbody>\n"
                + "                  </table>\n"
                + "                </div>\n"
                + "              </div>\n"
                + "            </div>\n"
                + "          </div>\n"
                + "        </div>\n"
                + "      </div>\n"
                + "    </div>\n"
                + "    <!-- END: Content-->\n"
                + "\n"
                + "\n"
                + "\n"
                + "    <?php include(\"../_layouts/scripts.php\") ?>\n \n"
                + "<script>\n"
                + "      function confirmDelete(id) {\n"
                + "        //console.log(\"id :\", id)\n"
                + "        Swal.fire({\n"
                + "          title: 'Are you sure?',\n"
                + "          text: \"You won't be able to revert this!\",\n"
                + "          icon: 'warning',\n"
                + "          showCancelButton: true,\n"
                + "          confirmButtonColor: '#3085d6',\n"
                + "          cancelButtonColor: '#d33',\n"
                + "          confirmButtonText: 'Yes, delete it!'\n"
                + "        }).then((result) => {\n"
                + "          console.log(result)\n"
                + "          if (result.isConfirmed) {\n"
                + "            window.location.href = \"Delete" + capitalize(classname) + ".php?id=<?php echo($" + classname + "['id']); ?>\";\n"
                + "          }\n"
                + "        })\n"
                + "      }\n"
                + "\n"
                + "    </script>"
                + "\n"
                + "</body>\n"
                + "<!-- END: Body-->\n"
                + "\n"
                + "\n"
                + "</html>";

        code.setText(resultDisplayUI);

    }

    @FXML
    private void previewUpdateUIAction(ActionEvent event) {
        String classname = name.getText();

        String[] arrayofvariables = columns.getText().split("\\r?\\n");
        length.setText(arrayofvariables.length + "");
        String inputs = "";
        for (int i = 0; i < arrayofvariables.length; i++) {
            if (i == 0) {
                inputs += "                                        <input type=\"hidden\" value=\"<?php echo $" + classname + "['" + arrayofvariables[i] + "']; ?>\" type=\"text\"\n"
                        + "                                            id=\"" + arrayofvariables[i] + "\" class=\"form-control\" placeholder=\"" + arrayofvariables[i] + "\" name=\"" + arrayofvariables[i] + "\">\n";
            } else {
                inputs += "                                        <div class=\"row\">\n"
                        + "                                            <div class=\"col-12\">\n"
                        + "                                                <div class=\"form-group\">\n"
                        + "                                                    <label for=\"" + arrayofvariables[i] + "\">" + capitalize(arrayofvariables[i]) + "</label>\n"
                        + "                                                    <div class=\"position-relative has-icon-left\">\n"
                        + "                                                        <input value=\"<?php echo $" + classname + "['" + arrayofvariables[i] + "']; ?>\" type=\"text\"\n"
                        + "                                                            id=\"" + arrayofvariables[i] + "\" class=\"form-control\" placeholder=\"" + capitalize(arrayofvariables[i]) + "\" name=\"" + arrayofvariables[i] + "\">\n"
                        + "                                                        <div class=\"form-control-position\">\n"
                        + "                                                            <i class=\"ft-user\"></i>\n"
                        + "                                                        </div>\n"
                        + "                                                    </div>\n"
                        + "                                                </div>\n"
                        + "                                            </div>\n"
                        + "                                        </div>\n \n";
            }

        }

        resultUpdateUI = "<!DOCTYPE html>\n"
                + "<html class=\"loading\" lang=\"en\" data-textdirection=\"ltr\">\n"
                + "\n"
                + "<head>\n"
                + "    <?php include(\"../_layouts/libs.php\") ?>\n"
                + "</head>\n"
                + "\n"
                + "\n"
                + "<body class=\"vertical-layout vertical-menu 2-columns   fixed-navbar\" data-open=\"click\" data-menu=\"vertical-menu\"\n"
                + "    data-color=\"bg-gradient-x-purple-blue\" data-col=\"2-columns\">\n"
                + "\n"
                + "    <?php include(\"../_layouts/header.php\") ?>\n"
                + "\n"
                + "    <!-- BEGIN: Content-->\n"
                + "    <div class=\"app-content content\">\n"
                + "        <div class=\"content-wrapper\">\n"
                + "            <div style=\"height:0px;\" class=\"content-wrapper-before\"></div>\n"
                + "            <div class=\"content-header row\">\n"
                + "            </div>\n"
                + "            <div class=\"content-body\">\n"
                + "\n"
                + "\n"
                + "\n"
                + "                <div class=\"col-12\">\n"
                + "                    <div class=\"card\">\n"
                + "                        <div class=\"card-header\">\n"
                + "                            <h4 class=\"card-title\" id=\"basic-layout-icons\">Modifier</h4>\n"
                + "                            <a class=\"heading-elements-toggle\">\n"
                + "                                <i class=\"la la-ellipsis-v font-medium-3\"></i>\n"
                + "                            </a>\n"
                + "\n"
                + "                        </div>\n"
                + "                        <div class=\"card-content collapse show\">\n"
                + "                            <div class=\"card-body\">\n"
                + "\n"
                + "                                <?php \n"
                + "                                    include(\"../../config.php\");\n"
                + "                                    include(\"../../Controllers/" + classname + "Controller.php\");\n"
                + "                                    $" + classname + "Controller = new " + classname + "Controller();\n"
                + "                                    $" + classname + " = $" + classname + "Controller->recuperer" + classname + "($_GET['id']);\n"
                + "                                ?>\n"
                + "\n"
                + "                                <form method=\"POST\" action=\"Update" + capitalize(classname) + ".php\" class=\"form\">\n"
                + "                                    <div class=\"form-body\">\n"
                + "\n"
                + inputs
                + "\n"
                + "                                    </div>\n"
                + "\n"
                + "                                    <div class=\"form-actions right\">\n"
                + "                                        <a href=\"index.php\" class=\"btn btn-danger mr-1\">\n"
                + "                                            <i class=\"ft-x\"></i> Cancel\n"
                + "                                        </a>\n"
                + "                                        <button type=\"submit\" class=\"btn btn-primary\">\n"
                + "                                            <i class=\"la la-check-square-o\"></i> Save\n"
                + "                                        </button>\n"
                + "                                    </div>\n"
                + "                                </form>\n"
                + "\n"
                + "                            </div>\n"
                + "                        </div>\n"
                + "                    </div>\n"
                + "                </div>\n"
                + "            </div>\n"
                + "        </div>\n"
                + "    </div>\n"
                + "\n"
                + "\n"
                + "\n"
                + "    <!-- END: Content-->\n"
                + "\n"
                + "\n"
                + "\n"
                + "    <?php include(\"../_layouts/scripts.php\") ?>\n"
                + "\n"
                + "</body>\n"
                + "<!-- END: Body-->\n"
                + "\n"
                + "\n"
                + "</html>";

        code.setText(resultUpdateUI);

    }

    @FXML
    private void previewUpdateServiceAction(ActionEvent event) {
        String classname = name.getText();
        String[] arrayofvariables = columns.getText().split("\\r?\\n");
        length.setText(arrayofvariables.length + "");
        String ifstatement = "";
        String construct = "";
        for (int i = 0; i < arrayofvariables.length; i++) {
            if (i == arrayofvariables.length - 1) {
                ifstatement += "isset($_POST['" + arrayofvariables[i] + "'])";
                construct += "$_POST['" + arrayofvariables[i] + "']";
            } else {
                ifstatement += "isset($_POST['" + arrayofvariables[i] + "']) and ";
                construct += "$_POST['" + arrayofvariables[i] + "'],";
            }
        }

        resultUpdate = "<?PHP\n"
                + "	include \"../../config.php\";\n"
                + "	include \"../../" + ModelsDir + "/" + classname + ".php\";\n"
                + "	include \"../../" + ControllersDir + "/" + classname + "Controller.php\";\n"
                + "\n"
                + "if (" + ifstatement + ")\n"
                + "{\n"
                + "	  $" + classname + "=new " + classname + "(" + construct + ");\n"
                + "\n"
                + "		$" + classname + "Controller=new " + classname + "Controller();\n"
                + "		$" + classname + "Controller->modifier" + classname + "($" + classname + ",$_POST['id']);\n"
                + "\n"
                + "		header('Location: index.php');\n"
                + "		//ob_end_clean();\n"
                + "	\n"
                + "}else{\n"
                + "	echo \"vérifier les champs\";\n"
                + "}\n"
                + "//*/\n"
                + "\n"
                + "?>";

        code.setText(resultUpdate);
    }

    private String capitalize(String name) {
        String s1 = name.substring(0, 1).toUpperCase();
        String nameCapitalized = s1 + name.substring(1);
        return nameCapitalized;
    }

}
