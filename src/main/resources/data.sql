INSERT INTO public.roles(id, name, description)VALUES ('ADMIN', 'administrador', 'administrador del sistema')
    ON CONFLICT (id) DO UPDATE SET name = excluded.name, description = excluded.description;

INSERT INTO public.roles(id, name, description)VALUES ('USER', 'usuario', 'usuario del sistema')
    ON CONFLICT (id) DO UPDATE SET name = excluded.name, description = excluded.description;

INSERT INTO public.roles(id, name, description)VALUES ('MODERATOR', 'moderador', 'moderador del sistema')
    ON CONFLICT (id) DO UPDATE SET name = excluded.name, description = excluded.description;

