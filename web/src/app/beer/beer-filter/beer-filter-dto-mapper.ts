import {Injectable} from '@angular/core';
import {FormGroup} from '@angular/forms';
import {BeerFilterFormFields} from './beer-filter-form-fields';
import {BeerFilterDto} from './beer-filter-dto';

@Injectable()
export class BeerFilterDtoMapper {
  public static toDto(formGroup: FormGroup) {
    let filterDto = new BeerFilterDto();
    filterDto.name = formGroup.get(BeerFilterFormFields.NAME).value;
    filterDto.breweryName = formGroup.get(BeerFilterFormFields.BREWERY_NAME).value;
    filterDto.barCode = formGroup.get(BeerFilterFormFields.BAR_CODE).value;
    return filterDto;
  }
}
