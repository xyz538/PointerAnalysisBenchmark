// Layer 1

class PluginRegistry {
    PluginDescriptor loadDescriptor(String id) {
        return new PluginDescriptor(id, this);
    }
}

// Layer 2

class PluginDescriptor {
    private final String id;
    private final PluginRegistry registry;

    PluginDescriptor(String id, PluginRegistry registry) {
        this.id = id;
        this.registry = registry;
    }

    PluginClassLoader createClassLoader() {
        return new PluginClassLoader(this);
    }

    String getId() {
        return id;
    }
}

// Layer 3
class PluginClassLoader {
    private final PluginDescriptor descriptor;

    PluginClassLoader(PluginDescriptor d) {
        this.descriptor = d;
    }

    PluginFactory createFactory() {
        return new PluginFactory(this);
    }

    PluginDescriptor getDescriptor() {
        return descriptor;
    }
}

// Layer4

class PluginFactory {
    private final PluginClassLoader loader;

    PluginFactory(PluginClassLoader l) {
        this.loader = l;
    }

    PluginInstance createInstance() {

        PluginInstance inst;

        String id = loader.getDescriptor().getId();
        if ("plugin.B".equals(id)) {
            inst = new DangerousPluginInstance(this);
        } else {
            inst = new PluginInstance(this);
        }
        assert (inst.getClass() == PluginInstance.class && inst.getClass() != DangerousPluginInstance.class);
        inst.start();
        return inst;
    }
}

// Layer 5
class PluginInstance {
    private final PluginFactory factory;

    PluginInstance(PluginFactory f) {
        this.factory = f;
    }

    void start() {
        // Activates the plugin by reading config, initializing logging/resources,
        // registering services/extensions/UI,
        // starting background tasks, validating dependencies, and setting up clean
        // shutdown.
    }
}

class DangerousPluginInstance extends PluginInstance {
    DangerousPluginInstance(PluginFactory f) {
        super(f);
    }

    @Override
    void start() {
        DangerousCode.run();
    }
}

class DangerousCode {

    static void run() {
        System.out.println("This never actually runs unless plugin.B is selected.");
    }
}

public class Main {
    public static void main(String[] args) {
        String id = System.getProperty("plugin.id", "plugin.A");
        PluginRegistry reg = new PluginRegistry();
        reg.loadDescriptor(id).createClassLoader().createFactory().createInstance();
    }
}