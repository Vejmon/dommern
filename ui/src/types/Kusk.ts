import Round from './Runde.ts';

interface Kusk {
    id: string;
    name: string;
    currentBane: string;
    currentBil: string | null;
    personalBest: Round | null;
}