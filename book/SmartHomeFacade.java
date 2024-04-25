
@RequireArgsConstructor
@Service
public class SmartHomeFacade {
    private Lights lights;
    private Aircon aircon;
    private Security security;
    private Televeision televeision;


    public void leaveHome() {
        lights.turnOffAll();
        aircon.off();
        security.arm();
    }

    public void arriveHome() {
        lights.turnOnEntrance();
        aircon.on();
        security.disarm();
    }
}