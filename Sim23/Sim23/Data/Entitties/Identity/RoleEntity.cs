﻿using Microsoft.AspNetCore.Identity;

namespace Sim23.Data.Entitties.Identity
{
    public class RoleEntity : IdentityRole<int>
    {
        public virtual ICollection<UserRoleEntity> UserRoles { get; set; }
    }
}
