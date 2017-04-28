package services;

import models.Setting;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Local
@Stateless
public class SettingsService {
    @PersistenceContext
    private EntityManager em;

    public String getSetting(String name) {
        Setting setting = em.find(Setting.class, name);
        if (setting != null) {
            return setting.getSettingValue();
        } else {
            return null;
        }
    }

    public void setSetting(String name, String value) {
        Setting setting = em.find(Setting.class, name);
        if (setting == null) {
            setting = new Setting();
            setting.setSettingName(name);
        }
        setting.setSettingValue(value);
        em.persist(setting);
    }
}
