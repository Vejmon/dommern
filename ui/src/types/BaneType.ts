export enum BaneType {
    BESTEFAR_BANEN = "BESTEFAR_BANEN",
    FRODE_SPESIAL = "FRODE_SPESIAL",
    UTEN_NAVN = "UTEN_NAVN",
    KORTESTE_VEIEN = "KORTESTE_VEIEN",
    UTENFOR_BANEN = "UTENFOR_BANEN",
    I_DEPO = "I_DEPO",
}


export function parseBaneType(value: string): BaneType | undefined {
    return (Object.values(BaneType) as string[]).includes(value) ? (value as BaneType) : undefined;
}