package login.filters;

import services.SettingsService;

import javax.ejb.EJB;

public class InstallFilter extends AuthFilter {
    @EJB
    private SettingsService settingsService;

    @Override
    String handleRequest() {
        Boolean alreadyInstalled = Boolean.valueOf(settingsService.getSetting("installed"));
        if (alreadyInstalled) {
            return "/index.xhtml";
        } else {
            return null;
        }
    }
}
