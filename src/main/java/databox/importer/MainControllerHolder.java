package databox.importer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import databox.importer.services.core.AbstractServiceController;

public class MainControllerHolder {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	private List<AbstractServiceController> controlersList = new ArrayList<>();

	private static MainControllerHolder instance;

	public static MainControllerHolder getInstance() {
		if (instance == null) {
			instance = new MainControllerHolder();
		}
		return instance;
	}

	public void addControler(AbstractServiceController controller) {
		if (controller == null) {
			logger.error("Controller is null.");
			return;
		}

		if (controlersList.contains(controller)) {
			logger.error("Controller already exists and will not be added.");
			return;
		}

		logger.debug("Adding controller: " + controller.getFullId());
		controlersList.add(controller);
		if (!controller.isAlive()) {
			controller.start();
		}
	}

	@SuppressWarnings("deprecation")
	public void removeControler(AbstractServiceController controller) {
		if (controller == null) {
			logger.error("Controller is null.");
			return;
		}

		logger.debug("Removing controller: " + controller.getFullId());
		controlersList.remove(controller);
		controller.stop();
	}

	public boolean cantainsController(String type, String userId) {
		return controlersList.stream().anyMatch(c -> c.idMatheches(AbstractServiceController.createId(type, userId)));
	}

	public AbstractServiceController getController(String type, String userId) {
		Optional<AbstractServiceController> controllerEntity = controlersList.stream().filter(c -> c.idMatheches(AbstractServiceController.createId(type, userId))).findFirst();
		return controllerEntity.isPresent() ? controllerEntity.get() : null;
	}

	public int getControlersSize() {
		return controlersList.size();
	}
}
