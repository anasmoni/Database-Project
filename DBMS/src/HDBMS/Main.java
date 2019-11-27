package HDBMS;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;

public class Main extends Application {

    Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        showLoginPage();
    }

    public void showLoginPage() throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("LogInAs.fxml"));
        Parent root = loader.load();

        stage.setTitle("ABC HOSPITAL");
        Scene sc1 = new Scene(root, 800, 450);

        FXMLLoader lor = new FXMLLoader();
        lor.setLocation(getClass().getResource("ReceptionistHome.fxml"));
        Parent rt = lor.load();

        Scene sc2 = new Scene(rt, 800, 450);

       FXMLLoader lr = new FXMLLoader();
        lr.setLocation(getClass().getResource("Appointment.fxml"));
        Parent r = lr.load();

        Scene app = new Scene(r, 800, 450);

        FXMLLoader rcprofile = new FXMLLoader();
        rcprofile.setLocation(getClass().getResource("Recep_Profile.fxml"));
        Parent rcprofile_root = rcprofile.load();

        FXMLLoader DPCLoader = new FXMLLoader();
        DPCLoader.setLocation(getClass().getResource("Doct_Profile.fxml"));
        Parent DPCroot = DPCLoader.load();

        FXMLLoader DCLoader = new FXMLLoader();
        DCLoader.setLocation(getClass().getResource("DoctHome.fxml"));
        Parent DCroot = DCLoader.load();

        FXMLLoader AUCLoader = new FXMLLoader();
        AUCLoader.setLocation(getClass().getResource("AppUpdate.fxml"));
        Parent AUCroot = AUCLoader.load();

        FXMLLoader AHCLoader = new FXMLLoader();
        AHCLoader.setLocation(getClass().getResource("AdminHome.fxml"));
        Parent AHCroot = AHCLoader.load();

        FXMLLoader ADDEmpLoader = new FXMLLoader();
        ADDEmpLoader.setLocation(getClass().getResource("AddEmployee.fxml"));
        Parent ADDEmproot = ADDEmpLoader.load();

        FXMLLoader ADMPLoader = new FXMLLoader();
        ADMPLoader.setLocation(getClass().getResource("AdminProfile.fxml"));
        Parent ADMProot = ADMPLoader.load();

        FXMLLoader ADNLoader = new FXMLLoader();
        ADNLoader.setLocation(getClass().getResource("AddNurse.fxml"));
        Parent ADNroot = ADNLoader.load();

        FXMLLoader ADDLoader = new FXMLLoader();
        ADDLoader.setLocation(getClass().getResource("AddDoctor.fxml"));
        Parent ADDroot = ADDLoader.load();

        FXMLLoader PurLoader = new FXMLLoader();
        PurLoader.setLocation(getClass().getResource("Purchase.fxml"));
        Parent Purroot = PurLoader.load();

        FXMLLoader CompLoader = new FXMLLoader();
        CompLoader.setLocation(getClass().getResource("Companies.fxml"));
        Parent Comproot = CompLoader.load();

        FXMLLoader ServLoader = new FXMLLoader();
        ServLoader.setLocation(getClass().getResource("Services.fxml"));
        Parent Servroot = ServLoader.load();

        FXMLLoader RoomLoader = new FXMLLoader();
        RoomLoader.setLocation(getClass().getResource("Rooms.fxml"));
        Parent Roomroot = RoomLoader.load();

        FXMLLoader TestLoader = new FXMLLoader();
        TestLoader.setLocation(getClass().getResource("Tests.fxml"));
        Parent Testroot = TestLoader.load();

        FXMLLoader OpLoader = new FXMLLoader();
        OpLoader.setLocation(getClass().getResource("Operation.fxml"));
        Parent Oproot = OpLoader.load();

        FXMLLoader PrescLoader = new FXMLLoader();
        PrescLoader.setLocation(getClass().getResource("Prescribe.fxml"));
        Parent Prescroot = PrescLoader.load();

        FXMLLoader PatLoader = new FXMLLoader();
        PatLoader.setLocation(getClass().getResource("Patient.fxml"));
        Parent Patroot = PatLoader.load();

        FXMLLoader PhrmLoader = new FXMLLoader();
        PhrmLoader.setLocation(getClass().getResource("PharmacistHome.fxml"));
        Parent Phrmroot = PhrmLoader.load();

        FXMLLoader PhrmProfLoader = new FXMLLoader();
        PhrmProfLoader.setLocation(getClass().getResource("PhrmProfile.fxml"));
        Parent PhrmProfroot = PhrmProfLoader.load();

        FXMLLoader PharmacyLoader = new FXMLLoader();
       PharmacyLoader.setLocation(getClass().getResource("Pharmacy.fxml"));
        Parent Pharmacyroot = PharmacyLoader.load();

       FXMLLoader CashProfLoader = new FXMLLoader();
     CashProfLoader.setLocation(getClass().getResource("CashProf.fxml"));
       Parent CashProfroot = CashProfLoader.load();

     FXMLLoader PathoLoader = new FXMLLoader();
     PathoLoader.setLocation(getClass().getResource("PathoHome.fxml"));
     Parent Pathoroot = PathoLoader.load();

     FXMLLoader PathProfLoader = new FXMLLoader();
     PathProfLoader.setLocation(getClass().getResource("PathoProf.fxml"));
     Parent PathoProfroot = PathProfLoader.load();

     FXMLLoader PathTestLoader = new FXMLLoader();
     PathTestLoader.setLocation(getClass().getResource("PathoTest.fxml"));
     Parent PathoTestroot = PathTestLoader.load();

        FXMLLoader CashHomeLoader = new FXMLLoader();
        CashHomeLoader.setLocation(getClass().getResource("CashierHome.fxml"));
        Parent CashHomeroot = CashHomeLoader.load();

        FXMLLoader CashBillLoader = new FXMLLoader();
        CashBillLoader.setLocation(getClass().getResource("CashBill.fxml"));
        Parent CashBillroot = CashBillLoader.load();


        ShowUI.PathoTest = new Scene(PathoTestroot, 800, 500);
        ShowUI.PathoProf = new Scene(PathoProfroot, 800, 500);
        ShowUI.PathoHome = new Scene(Pathoroot, 800, 500);

        ShowUI.CashHome = new Scene(CashHomeroot, 800, 500);
        ShowUI.CashProf = new Scene(CashProfroot, 800, 500);
        ShowUI.CashBill = new Scene(CashBillroot, 800, 500);

        ShowUI.Pharmacy = new Scene(Pharmacyroot, 800, 500);
        ShowUI.PhrmProf = new Scene(PhrmProfroot, 800, 500);
        ShowUI.PhrmHome = new Scene(Phrmroot, 800, 500);

        ShowUI.Patient = new Scene(Patroot, 800, 500);
        ShowUI.Prescribe = new Scene(Prescroot, 800, 500);
        ShowUI.Operations = new Scene(Oproot, 800, 500);
        ShowUI.Tests = new Scene(Testroot, 800, 500);
        ShowUI.Rooms = new Scene(Roomroot, 800, 500);
        ShowUI.Services = new Scene(Servroot, 800, 500);
        ShowUI.Companies = new Scene(Comproot, 800, 500);
        ShowUI.PurchaseScene = new Scene(Purroot, 800, 500);
        ShowUI.AddDoct = new Scene(ADDroot, 800, 500);
        ShowUI.AddNurse = new Scene(ADNroot, 800, 450);
        ShowUI.Admin_Profile = new Scene(ADMProot, 800, 450);
        ShowUI.AddEmpScene = new Scene(ADDEmproot, 800, 450);
        ShowUI.AdminHomeScene = new Scene(AHCroot, 800, 450);
        ShowUI.DPCscene = new Scene(DPCroot, 800, 450);
        ShowUI.Doct_Home = new Scene(DCroot, 800, 450);
        ShowUI.AppUpdateScene = new Scene(AUCroot, 800, 450);
        ShowUI.Recp_Profile = new Scene(rcprofile_root, 800, 450);
        ShowUI.appo = app;
        ShowUI.scene1 = sc1;
        ShowUI.scene2 = sc2;
        ShowUI.stage = stage;

        LoginController LC = loader.getController();
        LC.setMain(this);

        AppController AC = lr.getController();
        AC.setMain(this);

        RcpController rcp = lor.getController();
        rcp.setMain(this);

       DoctProfController DPC = DPCLoader.getController();
        DPC.setMain(this);

        DoctController DC = DCLoader.getController();
        DC.setMain(this);

        AppUpdateController AUC = AUCLoader.getController();
        AUC.setMain(this);

        AdminHomeController AHC = AHCLoader.getController();
        AHC.setMain(this);

        AddEmployeeController ADEmp = ADDEmpLoader.getController();
        ADEmp.setMain(this);

        AdminProfController ADPC = ADMPLoader.getController();
        ADPC.setMain(this);

        AddNurseController ADN = ADNLoader.getController();
        ADN.setMain(this);

        AddDoctController ADD = ADDLoader.getController();
        ADD.setMain(this);

        PurchaseController PC = PurLoader.getController();
        PC.setMain(this);

        CompanyController CC  = CompLoader.getController();
        CC.setMain(this);

        SeviceController Serv = ServLoader.getController();
        Serv.setMain(this);

        RoomController RC = RoomLoader.getController();
        RC.setMain(this);

        TestsController TC = TestLoader.getController();
        TC.setMain(this);

        OperationController OC = OpLoader.getController();
        OC.setMain(this);

        PrescribeController PrecC = PrescLoader.getController();
        PrecC.setMain(this);

        PatientController PatC = PatLoader.getController();
        PatC.setMain(this);

        PhrmHomeController Phrm = PhrmLoader.getController();
        Phrm.setMain(this);

        PhrmProfController PhrmC = PhrmProfLoader.getController();
        PhrmC.setMain(this);

        PharmacyController PhmcyC = PharmacyLoader.getController();
        PhmcyC.setMain(this);

        PathoHomecontroller PHC = PathoLoader.getController();
        PHC.setMain(this);

        PathoProfController PPC =  PathProfLoader.getController();
        PPC.setMain(this);

        PathoTestcontroller PTC = PathTestLoader.getController();
        PTC.setMain(this);

        CashierHomController CHC = CashHomeLoader.getController();
        CHC.setMain(this);

        CashProfcontroller CPF = CashProfLoader.getController();
        CPF.setMain(this);

        CashBillController CBC = CashBillLoader.getController();
        CBC.setMain(this);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
