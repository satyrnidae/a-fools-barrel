package dev.satyrn.foolsbarrel.fabriclike.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.satyrn.foolsbarrel.config.ClientSideConfig;
import me.shedaniel.autoconfig.AutoConfig;

public class ModMenuEntrypoint implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> AutoConfig.getConfigScreen(ClientSideConfig.class, parent).get();
	}
}
