interface Round {
    id: string;
    start: string; // ISO date string
    stop: string;  // ISO date string
    baneType: string;
    tid: number;
    bil: string | null;
    laup: string | null;
}