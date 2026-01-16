import type {Round} from './Round.ts';
import type {BaneType} from './BaneType.ts';

export interface Kusk {
    id: string;
    name: string;
    email: string;
    currentBane: BaneType;
    latest: Round | null;
    currentBil: string | null;
    personalBest: Round | null;
}