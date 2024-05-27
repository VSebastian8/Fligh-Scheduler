package enums;

public enum Cities {
    Frankfurt(Countries.Germany), Berlin(Countries.Germany), Munich(Countries.Germany), Marseille(Countries.France), Bucharest(Countries.Romania),
    London(Countries.England), Seville(Countries.Spain), Madrid(Countries.Spain), Vientiane(Countries.Laos);

    final Countries country;

    Cities(Countries country) {
        this.country = country;
    }

    public Countries getCountry() {
        return this.country;
    }
}
