import Round from './Runde.ts';
import BaneType from './BaneType.ts';

interface Kusk {
    id: string;
    name: string;
    currentBane: BaneType;
    currentBil: string | null;
    personalBest: Round | null;
}