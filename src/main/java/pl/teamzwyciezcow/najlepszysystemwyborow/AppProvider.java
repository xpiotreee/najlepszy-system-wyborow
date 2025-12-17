package pl.teamzwyciezcow.najlepszysystemwyborow;

import pl.teamzwyciezcow.najlepszysystemwyborow.controllers.NavigationController;
import pl.teamzwyciezcow.najlepszysystemwyborow.repositories.impl.AdminRepositoryImpl;
import pl.teamzwyciezcow.najlepszysystemwyborow.repositories.impl.CandidateRepositoryImpl;
import pl.teamzwyciezcow.najlepszysystemwyborow.repositories.impl.ElectionRepositoryImpl;
import pl.teamzwyciezcow.najlepszysystemwyborow.repositories.impl.UserRepositoryImpl;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.AdminService;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.CandidateService;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.ElectionService;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.UserService;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.impl.AdminServiceImpl;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.impl.CandidateServiceImpl;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.impl.ElectionServiceImpl;
import pl.teamzwyciezcow.najlepszysystemwyborow.services.impl.UserServiceImpl;

public class AppProvider {
    private static AppProvider instance;
    private NavigationController mainController;
    private AdminService adminService;
    private UserService userService;
    private ElectionService electionService;
    private CandidateService candidateService;

    private AppProvider() {
        this.adminService = new AdminServiceImpl(new AdminRepositoryImpl());
        this.userService = new UserServiceImpl(new UserRepositoryImpl());
        this.electionService = new ElectionServiceImpl(new ElectionRepositoryImpl());
        this.candidateService = new CandidateServiceImpl(new CandidateRepositoryImpl());
    }

    public static AppProvider getInstance() {
        if (instance == null) {
            AppProvider.instance = new AppProvider();
        }

        return instance;
    }

    public NavigationController getMainController() {
        return mainController;
    }

    public void setMainController(NavigationController mainController) {
        this.mainController = mainController;
    }

    public AdminService getAdminService() {
        return adminService;
    }

    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public ElectionService getElectionService() {
        return electionService;
    }

    public void setElectionService(ElectionService electionService) {
        this.electionService = electionService;
    }

    public CandidateService getCandidateService() {
        return candidateService;
    }

    public void setCandidateService(CandidateService candidateService) {
        this.candidateService = candidateService;
    }
}
