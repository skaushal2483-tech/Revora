package revora.manager;

import java.util.ArrayList;
import java.util.Iterator;
import revora.model.Owner;

public class OwnerManager {

    private ArrayList<Owner> owners;
    private int ownerCounter;

    public OwnerManager() {
        owners = new ArrayList<>();
        ownerCounter = 1001;
    }

    public String generateOwnerId() {
        return "OWN" + ownerCounter++;
    }

    public boolean addOwner(Owner owner) {

        if (findOwnerByEmail(owner.getEmail()) != null) {
            return false;
        }

        if (findOwnerByPhone(owner.getPhoneNumber()) != null) {
            return false;
        }

        owners.add(owner);
        return true;
    }

    public boolean deleteOwner(String ownerId) {

        Iterator<Owner> iterator = owners.iterator();

        while (iterator.hasNext()) {

            Owner owner = iterator.next();

            if (owner.getOwnerId().equalsIgnoreCase(ownerId)) {
                iterator.remove();
                return true;
            }
        }

        return false;
    }

    public Owner findOwnerByEmail(String email) {

        for (Owner owner : owners) {

            if (owner.getEmail().equalsIgnoreCase(email)) {
                return owner;
            }
        }

        return null;
    }

    public Owner findOwnerByPhone(String phoneNumber) {

        for (Owner owner : owners) {

            if (owner.getPhoneNumber().equals(phoneNumber)) {
                return owner;
            }
        }

        return null;
    }

    public ArrayList<Owner> getAllOwners() {
        return new ArrayList<>(owners);
    }

    public int getTotalOwners() {
        return owners.size();
    }
}