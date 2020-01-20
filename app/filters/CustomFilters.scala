package filters

import javax.inject.Inject
import play.api.Configuration
import play.api.http.HttpFilters

class CustomFilters @Inject()(configuration: Configuration,
                              securityHeadersFilter: CustomSecurityHeadersFilter
                             )
  extends HttpFilters
{

  val filters = Seq(securityHeadersFilter)
}
