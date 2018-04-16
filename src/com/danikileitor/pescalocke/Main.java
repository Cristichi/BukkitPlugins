package com.danikileitor.pescalocke;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{

	private AjusteBool[] ajustesBool = new AjusteBool[]{
			new AjusteBool("giveAllOnRespawn", false),
			new AjusteBool("giveAllOnFirstJoin", true)
	};

	private AjusteString[] ajustesStrings = new AjusteString[]{
			new AjusteString("lang", "en")
	};

	private Texto[]strings = new Texto[]{
			new Texto("pluginCargado", "Plugin cargado correctamente (Espa�ol)", "Plugin loaded"),

			new Texto("generadaPor", "Generada por Pescalocke", "Generated by Pescalocke"),
			new Texto("generadoPor", "Generado por Pescalocke", "Generated by Pescalocke"),

			new Texto("lorePRandom", "�Aleatoriza tu mundo!", "Randomize your world!"),
			new Texto("lorePNombres", "�Pon tu nombre en todo!", "The world is yours!"),
			new Texto("lorePMontame", "�Me subo encima tuya!", "Let me sit there!"),
			new Texto("lorePMontate", "�Puedes ser mi sombrero!", "Come here and be my hat!"),

			new Texto("nombrePaloRandom", "�4�KCa�a de Pescar 1�6�O RANDOM", "�4�KFishing Rod 1�6�O RANDOM"),
			new Texto("nombrePaloNombres", "�4�KCa�a de Pescar 2�6�O NOMBRES", "�4�KFishing Rod 2�6�O RENAMES"),
			new Texto("nombrePaloMontame", "�4�KCa�a de Pescar 3�6�O MONTAME", "�4�KFishing Rod 3�6�O MOUNT ON THAT"),
			new Texto("nombrePaloMontate", "�4�KCa�a de Pescar 4�6�O MONTATE", "�4�KFishing Rod 4�6�O MOUNT ON ME"),

			new Texto("palosRecibidos", "Disfruta de tus nuevas Ca�as de pescar", "Enjoy your new Fishing Rods"),

			new Texto("autoMontarte", "No puedes montarte sobre ti mismo", "You cannot mount on yourself"),
			new Texto("montarMontado", "No puedes montarte sobre eso", "You cannot mount on that"),

			new Texto("teHanPescado", "Te ha pescado ARG0 y te ha cambiado por un ARG1", "You have been caught by ARG0 and replaced with a AGR1"),
			new Texto("hasPescadoAJugador", "Has pescado a ARG0 y lo has cambiado por un ARG1", "You have caught ARG0 and replaced him with a AGR1"),
			new Texto("hasTransformadoUnMob", "Has transformado un ARG0 en un ARG1", "You transformed a ARG0 into a ARG1"),

			new Texto("hasPescadoAlgo", "Has pescado un ARG0", "You have caught a ARG0"),

			new Texto("jugadorNoEncontrado", "El jugador ARG0 no existe", "Player ARG0 does not exist")

	};

	private String getTexto(String clave, String... argumentos){
		reloadConfig();
		for (int i = 0; i < strings.length; i++) {
			if (clave.equalsIgnoreCase(strings[i].getClave())){
				String sol;
				if (getConfig().getString("lang").equalsIgnoreCase("es")){
					sol= strings[i].getSpanish();
				}else{
					sol= strings[i].getEnglish();
				}
				for (int j = 0; j < argumentos.length; j++) {
					sol= sol.replace("ARG"+j, argumentos[j]);
				}
				return sol;
			}
		}
		throw new Error404("Clave not found, notify this to cristichi@hotmail.es: "
				+ "clave="+clave);
	}

	private final String NOMBRE_PL= "Random Fishing";
	private final String MSG= "�2["+NOMBRE_PL+"]�A ";

	public ItemStack getPaloRandom(){
		ItemStack item = new ItemStack(Material.FISHING_ROD, 1);

		ItemMeta propied = item.getItemMeta();
		ArrayList<String> lores = new ArrayList<>();
		lores.add(getTexto("lorePRandom"));
		lores.add(getTexto("generadaPor"));
		propied.setLore(lores);
		propied.addEnchant(Enchantment.LUCK, Enchantment.LUCK.getMaxLevel(), true);
		propied.addEnchant(Enchantment.LURE, Enchantment.LURE.getMaxLevel(), true);
		propied.addEnchant(Enchantment.MENDING, Enchantment.MENDING.getMaxLevel(), true);
		propied.addEnchant(Enchantment.DURABILITY, Enchantment.DURABILITY.getMaxLevel(), true);
		propied.setDisplayName(getTexto("nombrePaloRandom"));
		item.setItemMeta(propied);

		return item;
	}

	public ItemStack getPaloNombres(){
		ItemStack item = new ItemStack(Material.FISHING_ROD, 1);

		ItemMeta propied = item.getItemMeta();
		ArrayList<String> lores = new ArrayList<>();
		lores.add(getTexto("lorePNombres"));
		lores.add(getTexto("generadaPor"));
		propied.setLore(lores);
		propied.addEnchant(Enchantment.LUCK, Enchantment.LUCK.getMaxLevel(), true);
		propied.addEnchant(Enchantment.LURE, Enchantment.LURE.getMaxLevel(), true);
		propied.addEnchant(Enchantment.MENDING, Enchantment.MENDING.getMaxLevel(), true);
		propied.addEnchant(Enchantment.DURABILITY, Enchantment.DURABILITY.getMaxLevel(), true);
		propied.setDisplayName(getTexto("nombrePaloNombres"));
		item.setItemMeta(propied);

		return item;
	}

	public ItemStack getPaloMontame(){
		ItemStack item = new ItemStack(Material.FISHING_ROD, 1);

		ItemMeta propied = item.getItemMeta();
		ArrayList<String> lores = new ArrayList<>();
		lores.add(getTexto("lorePMontame"));
		lores.add(getTexto("generadaPor"));
		propied.setLore(lores);
		propied.addEnchant(Enchantment.LUCK, Enchantment.LUCK.getMaxLevel(), true);
		propied.addEnchant(Enchantment.LURE, Enchantment.LURE.getMaxLevel(), true);
		propied.addEnchant(Enchantment.MENDING, Enchantment.MENDING.getMaxLevel(), true);
		propied.addEnchant(Enchantment.DURABILITY, Enchantment.DURABILITY.getMaxLevel(), true);
		propied.setDisplayName(getTexto("nombrePaloMontame"));
		item.setItemMeta(propied);

		return item;
	}

	public ItemStack getPaloMontate(){
		ItemStack item = new ItemStack(Material.FISHING_ROD, 1);

		ItemMeta propied = item.getItemMeta();
		ArrayList<String> lores = new ArrayList<>();
		lores.add(getTexto("lorePMontate"));
		lores.add(getTexto("generadaPor"));
		propied.setLore(lores);
		propied.addEnchant(Enchantment.LUCK, Enchantment.LUCK.getMaxLevel(), true);
		propied.addEnchant(Enchantment.LURE, Enchantment.LURE.getMaxLevel(), true);
		propied.addEnchant(Enchantment.MENDING, Enchantment.MENDING.getMaxLevel(), true);
		propied.addEnchant(Enchantment.DURABILITY, Enchantment.DURABILITY.getMaxLevel(), true);
		propied.setDisplayName(getTexto("nombrePaloMontate"));
		item.setItemMeta(propied);

		return item;
	}

	@Override
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		super.onEnable();
	}

	@Override
	public void onLoad() {
		for (int i = 0; i < ajustesBool.length; i++) {
			if (!getConfig().isSet(ajustesBool[i].clave)){
				getConfig().set(ajustesBool[i].clave, ajustesBool[i].porDefecto);
				getServer().broadcastMessage(MSG+" Ajustado "+ajustesBool[i].clave+" al valor por defecto ("+ajustesBool[i].porDefecto+")");
			}else{
				getConfig().set(ajustesBool[i].clave, getConfig().getBoolean(ajustesBool[i].clave));
			}
		}
		for (int i = 0; i < ajustesStrings.length; i++) {
			if (!getConfig().isSet(ajustesStrings[i].clave)){
				getConfig().set(ajustesStrings[i].clave, ajustesStrings[i].porDefecto);
				getServer().broadcastMessage(MSG+" Ajustado "+ajustesBool[i].clave+" al valor por defecto ("+ajustesBool[i].porDefecto+")");
			}else{
				getConfig().set(ajustesStrings[i].clave, getConfig().getString(ajustesStrings[i].clave));
			}
		}
		saveConfig();
		getServer().broadcastMessage(MSG+getTexto("pluginCargado"));
		super.onLoad();
	}

	@Override
	public void onDisable() {
		saveConfig();
		super.onDisable();
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		if (!player.hasPlayedBefore()){
			PlayerInventory inventory = player.getInventory();
			ItemStack itemstack = getPaloRandom();
			inventory.addItem(itemstack);
			itemstack = getPaloNombres();
			inventory.addItem(itemstack);
			itemstack = getPaloMontate();
			inventory.addItem(itemstack);
			itemstack = getPaloMontame();
			inventory.addItem(itemstack);
			player.sendMessage(MSG+getTexto("palosRecibidos"));
		}
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		reloadConfig();
		Boolean configDarTodoRespawn = getConfig().getBoolean(ajustesBool[0].clave);

		if (configDarTodoRespawn){
			Player player = e.getPlayer();
			PlayerInventory inventory = player.getInventory();
			ItemStack itemstack = getPaloRandom();
			inventory.addItem(itemstack);
			itemstack = getPaloNombres();
			inventory.addItem(itemstack);
			itemstack = getPaloMontate();
			inventory.addItem(itemstack);
			itemstack = getPaloMontame();
			inventory.addItem(itemstack);
			player.sendMessage(MSG+getTexto("palosRecibidos"));
		}
	}

	/*
	@EventHandler
	public void onEntityHit(EntityDamageByEntityEvent e){
		if (e.getDamager() instanceof Player){
			getServer().broadcastMessage(e.getDamager().getName()+" > "+e.getEntity().getName());
			Player p = (Player) e.getDamager();
			@SuppressWarnings("deprecation")
			ItemStack mano = p.getItemInHand();
			ItemStack palo = getPaloRandom();
			if (mismoItem(mano, palo)){

			}
		}
	}
	 */

	public boolean tieneEseNombre(ItemStack a, String clave){
		String n1;
		if (a.hasItemMeta())
			n1= a.getItemMeta().getDisplayName();
		else
			n1 = a.getType().name();

		for (int i = 0; i < strings.length; i++) {
			if (clave.equalsIgnoreCase(strings[i].getClave())){
				return n1.equals(strings[i].getEnglish()) || n1.equals(strings[i].getSpanish());
			}
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerFish(PlayerFishEvent evento) {
		Entity pescao = evento.getCaught();
		if (pescao==null){
			return;
		}
		Player jugador = evento.getPlayer();

		pescao.isGlowing();
		ItemStack mano = jugador.getItemInHand();


		if (tieneEseNombre(mano, "nombrePaloNombres")){
			pescao.setCustomName("�6"+jugador.getDisplayName());
			pescao.setCustomNameVisible(true);

		}else if (tieneEseNombre(mano, "nombrePaloMontame")){
			if (pescao instanceof Player){
				Player pescado = (Player) pescao;
				if (pescado.getPlayerListName().equals(jugador.getPlayerListName())){
					jugador.sendMessage(MSG+getTexto("autoMontarte"));
					return;
				}
			}
			List<Entity> montados = jugador.getPassengers();
			for (int i = 0; i < montados.size(); i++) {
				UUID mont = montados.get(i).getUniqueId();
				UUID pesc = pescao.getUniqueId();
				if (mont==pesc){
					jugador.sendMessage(MSG+getTexto("montarMontado"));
					return;
				}
			}
			for (int i = 0; i < montados.size(); i++) {
				pescao.addPassenger(montados.get(i));
			}
			pescao.addPassenger(jugador);

		}else if (tieneEseNombre(mano, "nombrePaloMontate")){
			if (pescao instanceof Player){
				Player pescado = (Player) pescao;
				if (pescado.getPlayerListName().equals(jugador.getPlayerListName())){
					jugador.sendMessage(MSG+getTexto("autoMontarte"));
					return;
				}
			}
			List<Entity> pasajeros = jugador.getPassengers();
			for (int i = 0; i < pasajeros.size(); i++) {
				UUID mont = pasajeros.get(i).getUniqueId();
				UUID pesc = pescao.getUniqueId();
				if (mont==pesc){
					jugador.sendMessage(MSG+getTexto("montarMontado"));
					return;
				}
			}
			if (pasajeros.isEmpty())
				jugador.addPassenger(pescao);
			else
				pasajeros.get(pasajeros.size()-1).addPassenger(pescao);
		}else if (tieneEseNombre(mano, "nombrePaloRandom")){
			boolean saleMob = false;
			if (Math.random()<0.2 || evento.getState().equals(State.CAUGHT_ENTITY)){
				saleMob=true;
			}

			final Location loc = jugador.getLocation();
			if (saleMob){
				EntityType[] mobs = EntityType.values();
				int rng = 0;

				Entity aparecida= null;
				boolean bien= false;
				while(!bien){
					try{
						rng = new Random().nextInt(mobs.length);
						switch (mobs[rng]) {
						case AREA_EFFECT_CLOUD:
						case ARROW:
						case FALLING_BLOCK:
						case LLAMA_SPIT:
						case LINGERING_POTION:
						case SHULKER_BULLET:
						case SPLASH_POTION:
						case SPECTRAL_ARROW:
						case TIPPED_ARROW:
						case ENDER_SIGNAL:
						case EGG:
							throw new Exception("Ese no vale");
						default:
							break;
						}
						aparecida = jugador.getWorld().spawnEntity(pescao.getLocation(), mobs[rng]);
						aparecida.setCustomName("�4Random "+mobs[rng].getEntityClass().getSimpleName());
						aparecida.setCustomNameVisible(true);
						bien=true;

						if (evento.getState().equals(State.CAUGHT_FISH)){
							final Entity finalAp= aparecida;
							aparecida.setGlowing(true);
							new Thread(new Runnable() {
								@Override
								public void run() {
									String name = finalAp.getCustomName();
									int ticks=10;
									for (int i = ticks; i > 0; i--) {
										finalAp.setCustomName(name+"�5 "+i);
										try {
											Thread.sleep(100);
										}catch(InterruptedException e) {}
									}
									finalAp.teleport(loc);
									finalAp.setCustomName(name);
									finalAp.setGlowing(false);
								}
							}).start();
						}
					}catch(Exception e){}
				}

				if (pescao instanceof Player){
					pescao.sendMessage(MSG+getTexto("teHanPescado", jugador.getName(), aparecida.getType().getEntityClass().getSimpleName()));
					jugador.sendMessage(MSG+getTexto("hasPescadoAJugador", pescao.getName(), aparecida.getType().getEntityClass().getSimpleName()));
				}else{
					jugador.sendMessage(MSG+getTexto("hasTransformadoUnMob", pescao.getType().getEntityClass().getSimpleName(), mobs[rng].getEntityClass().getSimpleName()));
				}
			}else{
				PlayerInventory inventory = jugador.getInventory();
				Material[] items = Material.values();
				ArrayList<Material> comidas = new ArrayList<>();
				for (int i = 0; i < items.length; i++) {
					if (items[i].isEdible()){
						comidas.add(items[i]);
					}
				}
				int rng=0;
				ItemStack item;
				if (Math.random()>0.1){
					rng = new Random().nextInt(comidas.size());
					item = new ItemStack(comidas.get(rng));
					jugador.sendMessage(MSG+getTexto("hasPescadoAlgo", comidas.get(rng).name().toLowerCase().replace("_", " ")));
				}else{
					rng = new Random().nextInt(items.length);
					item = new ItemStack(items[rng]);
					jugador.sendMessage(MSG+getTexto("hasPescadoAlgo", items[rng].name().toLowerCase().replace("_", " ")));
				}

				ItemMeta propied = item.getItemMeta();
				switch (item.getType()) {
				case BOOK:
					Enchantment[] encs = Enchantment.values();
					int numRandom = new Random().nextInt(40);
					for (int i = 0; i < numRandom; i++) {
						int rng2 = new Random().nextInt(encs.length);
						propied.addEnchant(encs[rng2], encs[rng2].getMaxLevel(), true);
					}
					break;

				default:
					break;
				}

				item.setItemMeta(propied);

				inventory.addItem(item);
			}
			if (pescao instanceof Player){
				pescao.teleport(loc);
			}else
				pescao.remove();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("darPaloRandom")){
			try{
				if (args.length!=1){
					args= new String[]{((Player)sender).getName()};
				}

				@SuppressWarnings("deprecation")
				Player player = Bukkit.getPlayer(args[0]);
				PlayerInventory inventory = player.getInventory();
				ItemStack itemstack = getPaloRandom();

				inventory.addItem(itemstack);

			}catch(Exception e){
				if (args.length!=1){
					return false;
				}
				try{
					Player tio = (Player)sender;
					tio.sendMessage(getTexto("playerNotFound", args[0]));
				}catch(Exception e1){
					getServer().broadcastMessage(MSG+getTexto("playerNotFound"));
				}
			}

			return true;
		}

		else if (command.getName().equalsIgnoreCase("darPaloNombres")){
			try{
				if (args.length!=1){
					args= new String[]{((Player)sender).getName()};
				}

				@SuppressWarnings("deprecation")
				Player player = Bukkit.getPlayer(args[0]);
				PlayerInventory inventory = player.getInventory();
				ItemStack itemstack = getPaloNombres();

				inventory.addItem(itemstack);

			}catch(Exception e){
				if (args.length!=1){
					return false;
				}
				try{
					Player tio = (Player)sender;
					tio.sendMessage(getTexto("playerNotFound", args[0]));
				}catch(Exception e1){
					getServer().broadcastMessage(MSG+getTexto("pluginCargado"));
				}
			}

			return true;
		}

		else if (command.getName().equalsIgnoreCase("darPaloMonturas")){
			try{
				if (args.length!=1){
					args= new String[]{((Player)sender).getName()};
				}

				@SuppressWarnings("deprecation")
				Player player = Bukkit.getPlayer(args[0]);
				PlayerInventory inventory = player.getInventory();
				ItemStack itemstack = getPaloMontame();
				inventory.addItem(itemstack);
				itemstack = getPaloMontate();
				inventory.addItem(itemstack);

			}catch(Exception e){
				if (args.length!=1){
					return false;
				}
				try{
					Player tio = (Player)sender;
					tio.sendMessage(getTexto("playerNotFound", args[0]));
				}catch(Exception e1){
					getServer().broadcastMessage(MSG+getTexto("playerNotFound"));
				}
			}

			return true;
		}

		return super.onCommand(sender, command, label, args);
	}
}
