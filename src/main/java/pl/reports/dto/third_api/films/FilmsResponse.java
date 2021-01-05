package pl.reports.dto.third_api.films;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FilmsResponse {
    public int count;
    public Object next;
    public Object previous;
    public List<Film> results;
}
