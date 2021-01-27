import {Injectable} from '@angular/core';
import {FormGroup} from '@angular/forms';
import {BreweryFilterFormFields} from './brewery-filter-form-fields';
import {BreweryFilterDto} from './brewery-filter-dto';

@Injectable()
export class BreweryFilterDtoMapper {
  public static toDto(formGroup: FormGroup) {
    let filterDto = new BreweryFilterDto();
    filterDto.breweryName = formGroup.get(BreweryFilterFormFields.BREWERY_NAME).value;
    return filterDto;
  }
}
