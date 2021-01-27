export class BeerRatingPdo {
  private _userIdentifier: string;

  get userIdentifier(): string {
    return this._userIdentifier;
  }

  set userIdentifier(value: string) {
    this._userIdentifier = value;
  }

  private _beerName: string;

  get beerName(): string {
    return this._beerName;
  }

  set beerName(value: string) {
    this._beerName = value;
  }

  private _beerId: string;

  get beerId(): string {
    return this._beerId;
  }

  set beerId(value: string) {
    this._beerId = value;
  }
}
