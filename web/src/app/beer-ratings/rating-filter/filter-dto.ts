export class FilterDto {
  private _userIdentifier: string;

  public get userIdentifier(): string {
    return this._userIdentifier;
  }

  public set userIdentifier(value: string) {
    this._userIdentifier = value;
  }

  private _beerName: string;

  public get beerName(): string {
    return this._beerName;
  }

  public set beerName(value: string) {
    this._beerName = value;
  }
}
